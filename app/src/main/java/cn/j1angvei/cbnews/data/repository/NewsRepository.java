package cn.j1angvei.cbnews.data.repository;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.j1angvei.cbnews.bean.News;
import cn.j1angvei.cbnews.data.local.NewsLocalSource;
import cn.j1angvei.cbnews.data.remote.NewsRemoteSource;
import cn.j1angvei.cbnews.exception.RAMItemNotFoundException;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/23.
 * store news like Article Headline Review into SQLiteDatabase
 */
public class NewsRepository<T extends News> extends Repository<T> {
    private static final String TAG = "NewsRepository";
    private final Map<String, List<T>> mNewsMap;

    public NewsRepository(NewsLocalSource<T> localSource, NewsRemoteSource<T> remoteSource) {
        super(localSource, remoteSource);
        mLocalSource = localSource;
        mRemoteSource = remoteSource;
        mNewsMap = new HashMap<>();
    }


    @Override
    public Observable<T> getDataFromDB(@NonNull final Integer page, final String id, @NonNull final String typeOrSN) {
        if (page < 0) {//page<0, retrieve from RAM
            List<T> newsList = mNewsMap.get(typeOrSN);
            return newsList == null || newsList.isEmpty() ? Observable.<T>error(new RAMItemNotFoundException()) : Observable.from(newsList);
        } else
            return mRemoteSource.fetchData(page, typeOrSN)
                    .doOnNext(new Action1<T>() {
                        @Override
                        public void call(T t) {
                            storeToDisk(t);
                        }
                    })
                    .onErrorResumeNext(new Func1<Throwable, Observable<? extends T>>() {
                        @Override
                        public Observable<? extends T> call(Throwable throwable) {
                            Log.e(TAG, "call: ", throwable);
                            return mLocalSource.read(page, id, typeOrSN);
                        }
                    })
                    .doOnNext(new Action1<T>() {
                        @Override
                        public void call(T t) {
                            storeToMemory(t);
                        }
                    });
    }

    @Override
    public Observable<T> offlineDownload(Integer page, String id, String typeOrSN) {
        return null;
    }

    @Override
    public void storeToDisk(T item) {
        mLocalSource.create(item);
    }

    @Override
    public void storeToMemory(T item) {
        List<T> newses = mNewsMap.get(item.getSourceType());
        if (newses == null) {
            newses = new ArrayList<>();
            mNewsMap.put(item.getSourceType(), newses);
        } else if (newses.contains(item)) {
            int idx = newses.indexOf(item);
            newses.add(idx, item);
        } else {
            newses.add(item);
        }
    }
}
