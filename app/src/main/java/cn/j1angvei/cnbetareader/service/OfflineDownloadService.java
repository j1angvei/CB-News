package cn.j1angvei.cnbetareader.service;

import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.List;

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
import cn.j1angvei.cnbetareader.util.PrefsUtil;
import rx.Observable;
import rx.Subscriber;
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
        int pages = mPrefsUtil.readIntDefault1(PrefsUtil.DOWNLOAD_PAGES);
        if (pages == 1) {
            pages = 2;
        }
        final int[] id = {1};
        final int maxProgress = 180 * pages;
        final NotificationManager manager = getNotificationMgr();
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle("download title")
                .setContentText("download in progress")
                .setSmallIcon(R.drawable.ic_file_download_black_24dp);

//        mArticleRepository.getData(null, Source.ALL.toString(), pages);
        Observable.range(1, pages)
                .flatMap(new Func1<Integer, Observable<? extends News>>() {
                    @Override
                    public Observable<? extends News> call(Integer integer) {
                        return Observable.merge(
                                mHeadlineRepository.getData(integer, null, Source.EDITORCOMMEND.toString()),
                                mReviewRepository.getData(integer, null, Source.JHCOMMENT.toString()),
                                mArticleRepository.getData(integer, null, Source.ALL.toString()));
                    }
                })
                .doOnNext(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        builder.setContentTitle("download news");
                        builder.setProgress(maxProgress, id[0]++, false);
                        manager.notify(2, builder.build());
                    }
                })
                .flatMap(new Func1<News, Observable<Content>>() {
                    @Override
                    public Observable<Content> call(News news) {
                        return mContentRepository.getData(0, news.getSid(), null);
                    }
                })
                .doOnNext(new Action1<Content>() {
                    @Override
                    public void call(Content content) {
                        builder.setContentTitle("download content");
                        builder.setProgress(maxProgress, id[0]++, false);
                        manager.notify(2, builder.build());
                    }
                })
                .flatMap(new Func1<Content, Observable<Comments>>() {
                    @Override
                    public Observable<Comments> call(Content content) {
                        return mCommentsRepository.getData(0, content.getSid(), content.getSn());
                    }
                })
                .doOnNext(new Action1<Comments>() {
                    @Override
                    public void call(Comments comments) {
                        builder.setContentTitle("download comments");
                        builder.setProgress(maxProgress, id[0]++, false);
                        manager.notify(2, builder.build());
                    }
                })
                .toList()
                .subscribe(new Subscriber<List<Comments>>() {
                    @Override
                    public void onCompleted() {
                        manager.cancel(2);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Comments> vc) {
                        builder.setProgress(0, 0, false);
                        manager.notify(2, builder.build());
                    }
                });

    }

}
