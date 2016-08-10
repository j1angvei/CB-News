package cn.j1angvei.cnbetareader.data.repository;

import java.util.Map;

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
    public Observable<Article> getData(String extra, Map<String, String> param) {
        return mRemoteSource.getData(extra, param)
                .doOnNext(new Action1<Article>() {
                    @Override
                    public void call(Article article) {
                        //save to cache or disk
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
