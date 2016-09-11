package cn.j1angvei.cbnews.data.repository;

import java.util.ArrayList;
import java.util.List;

import cn.j1angvei.cbnews.data.local.LocalSource;
import cn.j1angvei.cbnews.data.remote.RemoteSource;
import cn.j1angvei.cbnews.exception.SQLItemNotFoundException;
import cn.j1angvei.cbnews.exception.WEBItemNotFoundException;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Wayne on 2016/7/23.
 * implement repository pattern
 */
public abstract class Repository<T> {
    private static final String TAG = "Repository";
    public static final int PAGE_REFRESH = -1;
    LocalSource<T> mLocalSource;
    RemoteSource<T> mRemoteSource;
    List<T> mCache;

    public Repository(LocalSource<T> localSource, RemoteSource<T> remoteSource) {
        mLocalSource = localSource;
        mRemoteSource = remoteSource;
        mCache = new ArrayList<>();
    }

    public Observable<T> getData(int page, String id, String extra) {
        return fromRAM(page, id, extra)
                .onErrorResumeNext(fromSQL(page, id, extra))
                .onErrorResumeNext(fromWEB(page, id, extra));
    }


    private Observable<T> fromSQL(int page, String id, String extra) {
        return mLocalSource.read(page, id, extra)
                .switchIfEmpty(Observable.<T>error(new SQLItemNotFoundException()));
    }


    private Observable<T> fromWEB(int page, String id, String extra) {
        return mRemoteSource.fetchData(page, id, extra)
                .onErrorResumeNext(Observable.<T>error(new WEBItemNotFoundException()))
                .doOnNext(new Action1<T>() {
                    @Override
                    public void call(T t) {
                        toSQL(t);
                        toRAM(t);
                    }
                });
    }

    abstract Observable<T> fromRAM(int page, String id, String extra);

    private void toRAM(T item) {
        if (item == null) return;
        if (!mCache.contains(item)) {
            mCache.add(item);
        } else {
            int idx = mCache.indexOf(item);
            mCache.set(idx, item);
        }
    }

    private void toSQL(T item) {
        mLocalSource.create(item);
    }
}
