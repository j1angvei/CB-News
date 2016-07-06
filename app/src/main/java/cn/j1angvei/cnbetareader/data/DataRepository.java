package cn.j1angvei.cnbetareader.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.bean.Headline;
import cn.j1angvei.cnbetareader.bean.Review;
import cn.j1angvei.cnbetareader.data.local.LocalDataSource;
import cn.j1angvei.cnbetareader.data.remote.RemoteDataSource;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Wayne on 2016/6/16.
 */
@Singleton
public class DataRepository implements Repository {
    private LocalDataSource mLocalDataSource;
    private RemoteDataSource mRemoteDataSource;

    private Map<String, List<Article>> mNewsMap;
    private Map<String, Content> mContentMap;
    private Map<String, Review> mReviewMap;
    private Map<String, Headline> mHeadlineMap;

    @Inject
    public DataRepository(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
        initDataContainer();
    }


    @Override
    public Observable<Article> getArticleFromSource(final String type, int page) {
        return mRemoteDataSource.getArticleFromSource(type, page)
                .doOnNext(new Action1<Article>() {
                    @Override
                    public void call(Article article) {
                        List<Article> articleList = mNewsMap.get(type);
                        if (articleList == null) {
                            articleList = new ArrayList<>();
                            mNewsMap.put(type, articleList);
                        }
                        articleList.add(article);
                    }
                });
    }

    @Override
    public Observable<Content> getContentFromSource(final String sid) {
        return mRemoteDataSource.getContentFromSource(sid)
                .doOnNext(new Action1<Content>() {
                    @Override
                    public void call(Content content) {
                        mContentMap.put(sid, content);
                    }
                });
    }

    @Override
    public Observable<Review> getReviewsFromSource(String type, int page) {
        return mRemoteDataSource.getReviewsFromSource(type, page)
                .doOnNext(new Action1<Review>() {
                    @Override
                    public void call(Review review) {
                        mReviewMap.put(review.getCommentId(), review);
                    }
                });
    }

    @Override
    public Observable<Headline> getHeadlinesFromSource(String type, int page) {
        return mRemoteDataSource.getHeadlinesFromSource(type, page)
                .doOnNext(new Action1<Headline>() {
                    @Override
                    public void call(Headline headline) {
                        mHeadlineMap.put(headline.getSid(), headline);
                    }
                });
    }

    @Override
    public void initDataContainer() {
        mNewsMap = new HashMap<>();
        mContentMap = new HashMap<>();
        mReviewMap = new HashMap<>();
        mHeadlineMap = new HashMap<>();
    }

    @Override
    public void toMemory(String source, Object item) {

    }

}
