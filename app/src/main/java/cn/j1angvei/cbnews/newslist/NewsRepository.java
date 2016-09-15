package cn.j1angvei.cbnews.newslist;

import android.support.v4.util.ArrayMap;

import java.util.ArrayList;
import java.util.List;

import cn.j1angvei.cbnews.base.LoadMode;
import cn.j1angvei.cbnews.base.LocalSource;
import cn.j1angvei.cbnews.base.RemoteSource;
import cn.j1angvei.cbnews.base.Repository;
import cn.j1angvei.cbnews.bean.News;
import cn.j1angvei.cbnews.exception.data.LoadCacheFailException;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/23.
 * store news like Article Headline Review into SQLiteDatabase
 */
public class NewsRepository<N extends News> extends Repository<N> {
    private static final String TAG = "NewsRepository";
    private final ArrayMap<String, Integer> mPageMap;

    public NewsRepository(LocalSource<N> localSource, RemoteSource<N> remoteSource) {
        super(localSource, remoteSource);
        mPageMap = new ArrayMap<>();
    }

    private void setPage(int mode, String type, int page) {
        if (mode == LoadMode.LOAD_CACHE && mPageMap.get(type) != null) return;
        mPageMap.put(type, page);
    }

    private int getPage(int mode, String type) {
        switch (mode) {
            case LoadMode.LOAD_CACHE:
                return 0;
            case LoadMode.LOAD_REFRESH:
                return 1;
            default:
                return mPageMap.get(type);
        }
    }

    @Override
    public Observable<N> getNews(final int mode, final String type) {
        final int page = getPage(mode, type);
        Observable<N> observable;
        switch (mode) {
            case LoadMode.LOAD_CACHE:
                observable = Observable.from(mCache)
                        .filter(new Func1<N, Boolean>() {
                            @Override
                            public Boolean call(N n) {
                                return type.equals(n.getType());
                            }
                        })
                        .switchIfEmpty(mLocalSource.read(page, null, type))
                        .switchIfEmpty(Observable.<N>error(new LoadCacheFailException()));
                break;
            case LoadMode.LOAD_REFRESH:
                final List<N> newCache = new ArrayList<>();
                observable = mRemoteSource.fetch(page, null, type)
                        .doOnNext(new Action1<N>() {
                            @Override
                            public void call(N n) {
                                newCache.add(n);
                            }
                        })
                        .doOnCompleted(new Action0() {
                            @Override
                            public void call() {
                                mCache = newCache;
                            }
                        });
                break;
            case LoadMode.LOAD_MORE:
                observable = mRemoteSource.fetch(page, null, type)
                        .doOnNext(new Action1<N>() {
                            @Override
                            public void call(N n) {
                                mCache.add(n);
                            }
                        });
                break;
            default:
                return super.getNews(mode, type);

        }
        return observable
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        if (mode != LoadMode.LOAD_CACHE) {
                            setPage(mode, type, page + 1);
                        }
                    }
                });
    }
}
