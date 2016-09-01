package cn.j1angvei.cnbetareader.service;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.bean.Comments;
import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.bean.Headline;
import cn.j1angvei.cnbetareader.bean.News;
import cn.j1angvei.cnbetareader.bean.Review;
import cn.j1angvei.cnbetareader.bean.Source;
import cn.j1angvei.cnbetareader.data.repository.CommentsRepository;
import cn.j1angvei.cnbetareader.data.repository.ContentRepository;
import cn.j1angvei.cnbetareader.data.repository.NewsRepository;
import cn.j1angvei.cnbetareader.di.component.DaggerServiceComponent;
import cn.j1angvei.cnbetareader.di.module.ServiceModule;
import cn.j1angvei.cnbetareader.util.ErrorUtil;
import cn.j1angvei.cnbetareader.util.MessageUtil;
import cn.j1angvei.cnbetareader.util.NetworkUtil;
import cn.j1angvei.cnbetareader.util.PrefsUtil;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/8/31.
 * download all news
 */

public class OfflineDownloadService extends BaseService {
    static final String TAG = "OfflineDownloadService";
    @Inject
    @Named(News.Type.ARTICLE)
    NewsRepository<Article> mArticleRepository;
    @Inject
    @Named(News.Type.HEADLINE)
    NewsRepository<Headline> mHeadlineRepository;
    @Inject
    @Named(News.Type.REVIEW)
    NewsRepository<Review> mReviewRepository;
    @Inject
    ContentRepository mContentRepository;
    @Inject
    CommentsRepository mCommentsRepository;
    @Inject
    PrefsUtil mPrefsUtil;
    @Inject
    NetworkUtil mNetworkUtil;

    private static NotificationManager MANAGER;
    private static NotificationCompat.Builder BUILDER;
    private static int MAX_PROCESS = 360;
    private static int CUR_PROCESS = 0;

    public OfflineDownloadService() {
        super(TAG);
    }

    @Override
    protected void doInjection() {
        mServiceComponent = DaggerServiceComponent.builder()
                .applicationComponent(getApplicationComponent())
                .serviceModule(new ServiceModule(this))
                .build();
        mServiceComponent.inject(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (!mNetworkUtil.isNetworkOn()) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    MessageUtil.toast(R.string.error_no_network, getApplicationContext());
                }
            });
            return;
        }
        int pages = mPrefsUtil.readIntDefault1(PrefsUtil.DOWNLOAD_PAGES);

        MAX_PROCESS = 180 * pages;
        MANAGER = getNotificationMgr();
        BUILDER = new NotificationCompat.Builder(this)
                .setContentTitle("Caching news.")
                .setContentText("download in progress")
                .setSmallIcon(R.drawable.ic_stat_logo);
        Observable.range(1, pages)
                .concatMap(new Func1<Integer, Observable<? extends News>>() {
                    @Override
                    public Observable<? extends News> call(Integer integer) {
                        return Observable.concat(
                                mArticleRepository.getData(integer, null, Source.ALL.toString()),
                                mHeadlineRepository.getData(integer, null, Source.EDITORCOMMEND.toString()),
                                mReviewRepository.getData(integer, null, Source.JHCOMMENT.toString())
                        );
                    }
                })
                .doOnNext(new UpdateAction<News>())
                .delay(1000, TimeUnit.MICROSECONDS)
                .concatMap(new Func1<News, Observable<Content>>() {
                    @Override
                    public Observable<Content> call(News news) {
                        return mContentRepository.getData(0, news.getSid(), null);
                    }
                })
                .doOnNext(new UpdateAction<Content>())
                .delay(100, TimeUnit.MICROSECONDS)
                .concatMap(new Func1<Content, Observable<Comments>>() {
                    @Override
                    public Observable<Comments> call(Content content) {
                        return mCommentsRepository.getData(0, content.getSid(), content.getSn());
                    }
                })
                .doOnNext(new UpdateAction<Comments>())
                .delay(1000, TimeUnit.MICROSECONDS)
                .count()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        MessageUtil.toast("Download complete", getApplicationContext());
                        BUILDER.setProgress(0, 0, false)
                                .setContentTitle("Cache complete")
                                .setContentText("");
                        MANAGER.notify(2, BUILDER.build());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                        e.printStackTrace();
                        BUILDER.setContentTitle("Download failure");
                        MANAGER.notify(2, BUILDER.build());
                        MessageUtil.toast(ErrorUtil.getErrorInfo(e), getApplicationContext());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        BUILDER.setProgress(integer, integer, false);
                        MANAGER.notify(2, BUILDER.build());
                    }
                });
    }

    private class UpdateAction<T> implements Action1<T> {

        @Override
        public void call(Object o) {
            String text = "Downloading in progress... ";
            if (o instanceof Article) {
                text += "Latest news";
            } else if (o instanceof Headline) {
                text += "Past headlines";
            } else if (o instanceof Review) {
                text += "Popular comments";
            }
            BUILDER.setContentText(text);
            BUILDER.setProgress(MAX_PROCESS, ++CUR_PROCESS, false);
            MANAGER.notify(2, BUILDER.build());
        }
    }

}
