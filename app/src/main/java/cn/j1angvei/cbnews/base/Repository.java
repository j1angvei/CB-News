package cn.j1angvei.cbnews.base;

import java.util.ArrayList;
import java.util.List;

import cn.j1angvei.cbnews.exception.IllegalArgumentsException;
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

    public static final String LOAD_CACHE = "cache";
    public static final String LOAD_REFRESH = "refresh";
    public static final String LOAD_MORE = "more";


    public Repository(LocalSource<T> localSource, RemoteSource<T> remoteSource) {
        mLocalSource = localSource;
        mRemoteSource = remoteSource;
        mCache = new ArrayList<>();
    }

    @Deprecated
    public Observable<T> getData(int page, String id, String extra, String loadMode) {
        return Observable.error(new IllegalArgumentsException());
    }

    private void toCache(T item) {
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

    public Observable<T> getNews(int mode, String type) {
        return Observable.error(new IllegalArgumentsException());
    }

    public Observable<T> getContent(String sid) {
        return null;
    }

    public Observable<T> getComments(String sid, String sn) {
        return null;
    }

    public Observable<T> getTopic(String letter) {
        return null;
    }

}
