package cn.j1angvei.cnbetareader.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.data.local.LocalDataSource;
import cn.j1angvei.cnbetareader.data.remote.RemoteDataSource;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Wayne on 2016/6/16.
 */
@Singleton
public class DataRepository implements DataSource {
    private LocalDataSource mLocalDataSource;
    private RemoteDataSource mRemoteDataSource;

    private Map<String, List<Article>> mNewsMap;
    private Map<String, Content> mContentMap;

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
                        addArticleToMemory(type, article);
                    }
                });
    }


    private void addArticleToMemory(String type, Article item) {

        List<Article> articleList = mNewsMap.get(type);
        if (articleList == null) {
            articleList = new ArrayList<>();
            mNewsMap.put(type, articleList);
        }
        articleList.add(item);
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

    private void initDataContainer() {
        mNewsMap = new HashMap<>();
        mContentMap = new HashMap<>();
    }

}
