package cn.j1angvei.cnbetareader.data.repository;

import java.util.Map;

import cn.j1angvei.cnbetareader.data.local.NewsLocalSource;
import cn.j1angvei.cnbetareader.data.remote.NewsRemoteSource;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Wayne on 2016/7/23.
 */
public class NewsRepository<T> implements Repository<T> {
    private final NewsLocalSource<T> mLocalSource;
    private final NewsRemoteSource<T> mRemoteSource;

    public NewsRepository(NewsLocalSource<T> localSource, NewsRemoteSource<T> remoteSource) {
        mLocalSource = localSource;
        mRemoteSource = remoteSource;
    }

    @Override
    public Observable<T> getData(String extra, Map<String, String> param) {
        return mRemoteSource.getData(extra, param)
                .doOnNext(new Action1<T>() {
                    @Override
                    public void call(T t) {
                        toDisk(t);
                    }
                });
    }

    @Override
    public void toDisk(T item) {
        mLocalSource.create(item);
    }

    @Override
    public void toRAM(T item) {
    }
}
