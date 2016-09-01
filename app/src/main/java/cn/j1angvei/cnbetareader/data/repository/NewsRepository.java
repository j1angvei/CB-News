package cn.j1angvei.cnbetareader.data.repository;

import cn.j1angvei.cnbetareader.bean.News;
import cn.j1angvei.cnbetareader.data.local.NewsLocalSource;
import cn.j1angvei.cnbetareader.data.remote.NewsRemoteSource;
import cn.j1angvei.cnbetareader.util.NetworkUtil;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Wayne on 2016/7/23.
 * store news like Article Headline Review into SQLiteDatabase
 */
public class NewsRepository<T extends News> extends Repository<T> {
    private static final String TAG = "NewsRepository";
    private final NewsLocalSource<T> mLocalSource;
    private final NewsRemoteSource<T> mRemoteSource;

    public NewsRepository(NewsLocalSource<T> localSource, NewsRemoteSource<T> remoteSource, NetworkUtil networkUtil) {
        super(networkUtil);
        mLocalSource = localSource;
        mRemoteSource = remoteSource;
    }


    @Override
    public Observable<T> getData(int page, String id, String typeOrSN) {
        return isConnected() ?
                mRemoteSource.loadData(page, typeOrSN)
                        .doOnNext(new Action1<T>() {
                            @Override
                            public void call(T t) {
                                storeToDisk(t);
                            }
                        }) :
                mLocalSource.read(page, id, typeOrSN);
    }

    @Override
    public void storeToDisk(T item) {
        mLocalSource.create(item);
    }

    @Override
    public void storeToMemory(T item) {
    }
}
