package cn.j1angvei.cbnews.base;

import java.util.ArrayList;
import java.util.List;

import cn.j1angvei.cbnews.exception.NoCacheException;
import cn.j1angvei.cbnews.exception.NoNetworkException;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/23.
 * implement repository pattern
 */
public abstract class Repository<T> {
    private static final String TAG = "Repository";
    protected LocalSource<T> mLocalSource;
    protected RemoteSource<T> mRemoteSource;
    protected List<T> mCache;

    public Repository(LocalSource<T> localSource, RemoteSource<T> remoteSource) {
        mLocalSource = localSource;
        mRemoteSource = remoteSource;
        mCache = new ArrayList<>();
    }

    public void updateLocal() {
        if (!mCache.isEmpty()) {
            mLocalSource.delete("*");
            for (T item : mCache) {
                mLocalSource.create(item);
            }
        }
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

    protected abstract Observable<T> filterCache(String sid);

}
