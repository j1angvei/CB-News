package cn.j1angvei.cnbetareader.data;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.bean.Comments;
import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.data.local.LocalDataSource;
import cn.j1angvei.cnbetareader.data.remote.RemoteDataSource;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Wayne on 2016/6/16.
 */
public class DataRepository<T> implements Repository<T> {
    private LocalDataSource<T> mLocalDataSource;
    private RemoteDataSource<T> mRemoteDataSource;


    public DataRepository(LocalDataSource<T> localDataSource, RemoteDataSource<T> remoteDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    @Override
    public Observable<Content> getContent(final String sid) {
        return mRemoteDataSource.getContent(sid)
                .doOnNext(new Action1<Content>() {
                    @Override
                    public void call(Content content) {
                    }
                });
    }

    @Override
    public Observable<Comments> getComments(String token, String op) {
        return null;
    }

    @Override
    public Observable<T> getNews(String type, int page) {
        return mRemoteDataSource.getNews(type, page)
                .doOnNext(new Action1<T>() {
                    @Override
                    public void call(T t) {
                        //add to memory
                    }
                });
    }

    @Override
    public Observable<Article> getTopicArticles(final String topicId, int page) {
        return mRemoteDataSource.getTopicArticles(topicId, page);
    }

    @Override
    public Observable<Topic> getTopics(final char letter) {
        return mRemoteDataSource.getTopics(letter);
    }

}
