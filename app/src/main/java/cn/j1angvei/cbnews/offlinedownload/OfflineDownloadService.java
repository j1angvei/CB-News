package cn.j1angvei.cbnews.offlinedownload;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;

import javax.inject.Inject;

import cn.j1angvei.cbnews.R;
import cn.j1angvei.cbnews.bean.Comments;
import cn.j1angvei.cbnews.bean.Content;
import cn.j1angvei.cbnews.bean.News;
import cn.j1angvei.cbnews.bean.Type;
import cn.j1angvei.cbnews.di.component.DaggerServiceComponent;
import cn.j1angvei.cbnews.di.module.ServiceModule;
import cn.j1angvei.cbnews.util.ErrorUtil;
import cn.j1angvei.cbnews.util.MessageUtil;
import cn.j1angvei.cbnews.util.NetworkUtil;
import cn.j1angvei.cbnews.util.PrefsUtil;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/8/31.
 * download all news
 */

public class OfflineDownloadService extends RepositoryService {
    static final String TAG = "OfflineDownloadService";

    @Inject
    PrefsUtil mPrefsUtil;
    @Inject
    NetworkUtil mNetworkUtil;

    private NotificationManager mManager;
    private NotificationCompat.Builder mBuilder;
    private int mMaxProcess = 360;
    private int mCurProcess = 0;

    public OfflineDownloadService() {
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
        int pages = 2;
        mMaxProcess = 180 * pages;
        mManager = getNotificationMgr();
        mBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.title_cache_news))
                .setContentText(getString(R.string.ph_download_progress))
                .setSmallIcon(R.drawable.ic_stat_logo);
        Observable<Integer> download = Observable.range(1, pages)
                .concatMap(new Func1<Integer, Observable<? extends News>>() {
                    @Override
                    public Observable<? extends News> call(Integer page) {
                        return Observable.concat(
                                mArticleRepository.download(Type.LATEST_NEWS, page),
                                mHeadlineRepository.download(Type.HEADLINE, page),
                                mReviewRepository.download(Type.REVIEW, page)
                        );
                    }
                })
                .doOnNext(new UpdateAction<News>())
                .concatMap(new Func1<News, Observable<Content>>() {
                    @Override
                    public Observable<Content> call(News news) {
                        return mContentRepository.download(news.getSid());
                    }
                })
                .doOnNext(new UpdateAction<Content>())
                .concatMap(new Func1<Content, Observable<Comments>>() {
                    @Override
                    public Observable<Comments> call(Content content) {
                        return mCmtRepository.download(content.getSid(), content.getSn());
                    }
                })
                .doOnNext(new UpdateAction<Comments>())
                .count()
                .observeOn(AndroidSchedulers.mainThread());

        download.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                MessageUtil.toast(getString(R.string.info_download_complete), getApplicationContext());
                mBuilder.setProgress(0, 0, false)
                        .setContentTitle(getString(R.string.title_cache_complete))
                        .setContentText(getString(R.string.info_all_data_cached));
                mManager.notify(2, mBuilder.build());
            }

            @Override
            public void onError(Throwable e) {
                mBuilder.setContentTitle(getString(R.string.error_cache_fail));
                mManager.notify(2, mBuilder.build());
                MessageUtil.toast(ErrorUtil.getErrorInfo(e), getApplicationContext());
            }

            @Override
            public void onNext(Integer integer) {
                mBuilder.setProgress(integer, integer, false);
                mManager.notify(2, mBuilder.build());
            }
        });
    }

    private class UpdateAction<T> implements Action1<T> {

        @Override
        public void call(Object o) {
            String text = String.format(getResources().getString(R.string.ph_download_progress), "" + mCurProcess, "" + mMaxProcess);
            mBuilder.setContentText(text);
            mBuilder.setProgress(mMaxProcess, ++mCurProcess, false);
            mManager.notify(2, mBuilder.build());
        }
    }

}
