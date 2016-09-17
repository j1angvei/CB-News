package cn.j1angvei.cbnews.base;

import java.util.ArrayList;
import java.util.List;

import cn.j1angvei.cbnews.exception.IllegalArgumentsException;
import cn.j1angvei.cbnews.exception.NoCacheException;
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

    protected void toCache(T item) {
        if (item == null) return;
        if (!mCache.contains(item)) {
            mCache.add(item);
        } else {
            int idx = mCache.indexOf(item);
            mCache.set(idx, item);
        }
    }

    private void toLocal(T item) {
        mLocalSource.create(item);
    }

    public void saveCache() {
        for (T item : mCache) {
            mLocalSource.create(item);
        }
    }

    @Deprecated
    public Observable<T> getNews(int mode, String type) {
        return Observable.error(new IllegalArgumentsException());
    }

    @Deprecated
    public Observable<T> getContent(int mode, String sid) {
        return Observable.error(new IllegalArgumentsException());
    }

    @Deprecated
    public Observable<T> getComments(int mode, String sid, String sn) {
        return Observable.error(new IllegalArgumentsException());
    }

    @Deprecated
    public Observable<T> getTopic(String id) {
        return Observable.error(new IllegalArgumentsException());
    }

    @Deprecated
    public Observable<T> getTopics(int page) {
        return Observable.error(new IllegalArgumentsException());
    }

    @Deprecated
    public Observable<T> loadCache(int page) {
        return null;
    }

    public Observable<T> getCache(String type) {
        return Observable.error(new NoCacheException());
    }

    public Observable<T> getLatest(String type) {
        return null;
    }

    public Observable<T> getMore(String type) {
        return null;
    }

    protected abstract Observable<T> filterCache(String type);
}
