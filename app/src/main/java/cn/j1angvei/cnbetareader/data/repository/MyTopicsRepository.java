package cn.j1angvei.cnbetareader.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.data.local.MyTopicsLocalSource;
import cn.j1angvei.cnbetareader.data.remote.MyTopicsRemoteSource;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Wayne on 2016/7/23.
 */
@Singleton
public class MyTopicsRepository implements Repository<Article> {
    private final MyTopicsLocalSource mLocalSource;
    private final MyTopicsRemoteSource mRemoteSource;

    @Inject
    public MyTopicsRepository(MyTopicsLocalSource localSource, MyTopicsRemoteSource remoteSource) {
        mLocalSource = localSource;
        mRemoteSource = remoteSource;
    }

    @Override
    public Observable<Article> get(int page, String... str) {
        return mRemoteSource.getItem(page, str)
                .doOnNext(new Action1<Article>() {
                    @Override
                    public void call(Article article) {
                        // add to toRAM
                    }
                });
    }

    @Override
    public void toDisk(Article item) {

    }

    @Override
    public void toRAM(Article item) {

    }
}
