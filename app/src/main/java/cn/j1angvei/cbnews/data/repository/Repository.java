package cn.j1angvei.cbnews.data.repository;

import cn.j1angvei.cbnews.data.local.LocalSource;
import cn.j1angvei.cbnews.data.remote.RemoteSource;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/23.
 * implement repository pattern
 */
public abstract class Repository<T> {
    LocalSource<T> mLocalSource;
    RemoteSource<T> mRemoteSource;

    public Repository(LocalSource<T> localSource, RemoteSource<T> remoteSource) {
        mLocalSource = localSource;
        mRemoteSource = remoteSource;
    }

    public abstract Observable<T> getDataFromDB(Integer page, String id, String typeOrSN);

    public abstract Observable<T> offlineDownload(Integer page, String id, String typeOrSN);

    public abstract void storeToDisk(T item);

    public abstract void storeToMemory(T item);
}
