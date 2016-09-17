package cn.j1angvei.cbnews.newslist;

import java.util.ArrayList;
import java.util.List;

import cn.j1angvei.cbnews.base.LocalSource;
import cn.j1angvei.cbnews.base.RemoteSource;
import cn.j1angvei.cbnews.base.Repository;
import cn.j1angvei.cbnews.bean.News;
import cn.j1angvei.cbnews.bean.Type;
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

    public NewsRepository(LocalSource<N> localSource, RemoteSource<N> remoteSource) {
        super(localSource, remoteSource);
    }

    @Override
    public Observable<N> getCache(String type) {
        return filterCache(type)
                .switchIfEmpty(mLocalSource.read(type))
                .switchIfEmpty(super.getCache(type));
    }

    @Override
    public Observable<N> getLatest(final String type) {
        final List<N> latestNews = new ArrayList<>();
        return mRemoteSource.getNews(1, type)
                .doOnNext(new Action1<N>() {
                    @Override
                    public void call(N n) {
                        latestNews.add(n);
                    }
                })
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        refreshCache(type, latestNews);
                    }
                });
    }

    @Override
    public Observable<N> getMore(final String type) {
        return getNextPage(type)
                .flatMap(new Func1<Integer, Observable<N>>() {
                    @Override
                    public Observable<N> call(Integer page) {
                        return mRemoteSource.getNews(page, type);
                    }
                })
                .doOnNext(new Action1<N>() {
                    @Override
                    public void call(N n) {
                        mCache.add(n);
                    }
                });
    }

    @Override
    protected Observable<N> filterCache(final String sid) {
        return Observable.from(mCache)
                .filter(new Func1<N, Boolean>() {
                    @Override
                    public Boolean call(N n) {
                        return sid.equals(n.getType());
                    }
                });
    }

    private Observable<Integer> getNextPage(final String type) {
        return filterCache(type)
                .count()
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer size) {
                        int perPage = (type.equals(Type.HEADLINE) || type.equals(Type.REVIEW)) ? 10 : 40;
                        return size / perPage + 1;
                    }
                });
    }

    private void refreshCache(String type, List<N> ns) {
        for (N n : mCache) {
            if (type.equals(n.getType())) {
                mCache.remove(n);
            }
        }
        mCache.addAll(ns);
    }

    @Override
    public void updateLocal() {
        List<String> deletedTypes = new ArrayList<>();
        for (N n : mCache) {
            String type = n.getType();
            if (deletedTypes.contains(type))
                continue;
            mLocalSource.delete(type);
            deletedTypes.add(type);
        }
        for (N n : mCache) {
            mLocalSource.create(n);
        }
    }
}
