package cn.j1angvei.cnbetareader.service;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;

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
    private boolean isCanceled;

    private NotificationManager MANAGER;
    private NotificationCompat.Builder BUILDER;
    private int MAX_PROCESS = 360;
    private int CUR_PROCESS = 0;

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
        if (!mNetworkUtil.isWifiOn()) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    MessageUtil.toast(R.string.info_need_wifi_env, getApplicationContext());
                }
            });
            return;
        }
        int pages = mPrefsUtil.readIntDefault1(PrefsUtil.DOWNLOAD_PAGES);

        MAX_PROCESS = 180 * pages;
        MANAGER = getNotificationMgr();
        BUILDER = new NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.title_cache_news))
                .setContentText(getString(R.string.ph_download_progress))
                .setSmallIcon(R.drawable.ic_stat_logo);
        Observable<Integer> download = Observable.range(1, pages)
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
                .concatMap(new Func1<News, Observable<Content>>() {
                    @Override
                    public Observable<Content> call(News news) {
                        return mContentRepository.getData(0, news.getSid(), null);
                    }
                })
                .doOnNext(new UpdateAction<Content>())
                .concatMap(new Func1<Content, Observable<Comments>>() {
                    @Override
                    public Observable<Comments> call(Content content) {
                        return mCommentsRepository.getData(0, content.getSid(), content.getSn());
                    }
                })
                .doOnNext(new UpdateAction<Comments>())
                .count()
                .observeOn(AndroidSchedulers.mainThread());

        download.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                MessageUtil.toast(getString(R.string.info_download_complete), getApplicationContext());
                BUILDER.setProgress(0, 0, false)
                        .setContentTitle(getString(R.string.title_cache_complete))
                        .setContentText(getString(R.string.info_all_data_cached));
                MANAGER.notify(2, BUILDER.build());
            }

            @Override
            public void onError(Throwable e) {
                BUILDER.setContentTitle(getString(R.string.error_cache_fail));
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
            String text = String.format(getResources().getString(R.string.ph_download_progress), "" + CUR_PROCESS, "" + MAX_PROCESS);
            BUILDER.setContentText(text);
            BUILDER.setProgress(MAX_PROCESS, ++CUR_PROCESS, false);
            MANAGER.notify(2, BUILDER.build());
        }
    }

}
