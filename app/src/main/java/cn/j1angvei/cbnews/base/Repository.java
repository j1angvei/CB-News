package cn.j1angvei.cbnews.base;

import java.util.HashSet;
import java.util.Set;

import cn.j1angvei.cbnews.exception.NoCacheException;
import cn.j1angvei.cbnews.exception.NoNetworkException;
import cn.j1angvei.cbnews.exception.OfflineDownloadException;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/23.
 * implement repository pattern
 */
public abstract class Repository<T> {
    private static final String TAG = "Repository";
    protected LocalSource<T> mLocalSource;
    protected RemoteSource<T> mRemoteSource;
    protected Set<T> mCache;

    public Repository(LocalSource<T> localSource, RemoteSource<T> remoteSource) {
        mLocalSource = localSource;
        mRemoteSource = remoteSource;
        mCache = new HashSet<>();
    }

    public void updateLocal() {
        if (!mCache.isEmpty()) {
            mLocalSource.delete("*");
            for (T item : mCache) {
                mLocalSource.create(item);
            }
        }
    }

    public void storeToDb(T item) {

    }

    public Observable<T> getCache(String id) {
        return Observable.error(new NoCacheException());
    }

    public Observable<T> getLatest(String type) {
        return Observable.error(new NoNetworkException());
    }

    public Observable<T> getLatest(String sid, String sn) {
        return Observable.error(new NoNetworkException());
    }

    public Observable<T> getMore(String type) {
        return Observable.error(new NoNetworkException());
    }

    public Observable<T> download(String type, int page) {
        return Observable.error(new OfflineDownloadException());
    }

    public Observable<T> download(String sid, String sn) {
        return Observable.error(new OfflineDownloadException());
    }

    public Observable<T> download(String sid) {

        return Observable.error(new OfflineDownloadException());
    }

    protected abstract Observable<T> filterCache(String sid);

}
