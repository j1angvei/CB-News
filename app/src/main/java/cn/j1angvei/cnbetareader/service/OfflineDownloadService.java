package cn.j1angvei.cnbetareader.service;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import javax.inject.Inject;
import javax.inject.Named;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.bean.Headline;
import cn.j1angvei.cnbetareader.bean.News;
import cn.j1angvei.cnbetareader.bean.Review;
import cn.j1angvei.cnbetareader.data.repository.CommentsRepository;
import cn.j1angvei.cnbetareader.data.repository.ContentRepository;
import cn.j1angvei.cnbetareader.data.repository.NewsRepository;
import cn.j1angvei.cnbetareader.di.component.DaggerServiceComponent;
import cn.j1angvei.cnbetareader.di.module.ServiceModule;
import cn.j1angvei.cnbetareader.util.MessageUtil;
import cn.j1angvei.cnbetareader.util.PrefsUtil;

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
        Log.d(TAG, "onHandleIntent: ");
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                MessageUtil.toast("service", getApplicationContext());
            }
        });
    }
}
