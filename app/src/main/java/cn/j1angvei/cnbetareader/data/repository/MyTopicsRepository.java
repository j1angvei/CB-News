package cn.j1angvei.cnbetareader.data.repository;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.data.local.MyTopicsLocalSource;
import cn.j1angvei.cnbetareader.data.remote.MyTopicsRemoteSource;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Wayne on 2016/7/23.
 */
public class MyTopicsRepository implements Repository<Article> {
    private final MyTopicsLocalSource mLocalSource;
    private final MyTopicsRemoteSource mRemoteSource;

    public MyTopicsRepository(MyTopicsLocalSource localSource, MyTopicsRemoteSource remoteSource) {
        mLocalSource = localSource;
        mRemoteSource = remoteSource;
    }

    @Override
    public Observable<Article> get(int page, String... str) {
        String topicId = str[0];
        return mRemoteSource.getItem(page, topicId)
                .doOnNext(new Action1<Article>() {
                    @Override
                    public void call(Article article) {
                        // add to cache
                    }
                });
    }

    @Override
    public void save(Article item) {

    }

    @Override
    public void cache(Article item) {

    }
}
