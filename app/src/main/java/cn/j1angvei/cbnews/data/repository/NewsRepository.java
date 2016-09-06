package cn.j1angvei.cbnews.data.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.j1angvei.cbnews.bean.News;
import cn.j1angvei.cbnews.data.local.NewsLocalSource;
import cn.j1angvei.cbnews.data.remote.NewsRemoteSource;
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
    public Observable<T> getDataFromDB(Integer page, String id, final String typeOrSN) {

        return Observable.just(page == -1)
                .flatMap(new Func1<Boolean, Observable<T>>() {
                    @Override
                    public Observable<T> call(Boolean refresh) {
                        List<T> newses = mNewsMap.get(typeOrSN);
                        return refresh || newses == null ?
                                Observable.<T>empty() :
                                Observable.from(newses);
                    }
                })
                .switchIfEmpty(mRemoteSource.fetchData(page, typeOrSN)
                        .doOnNext(new Action1<T>() {
                            @Override
                            public void call(T t) {
                                storeToMemory(t);
                                storeToDisk(t);
                            }
                        }));
    }

    @Override
    public Observable<T> offlineDownload(Integer page, String id, String typeOrSN) {
        return null;
    }

    @Override
    public Observable<T> getDataFromRAM(Integer page, String id, String typeOrSN) {
        return null;
    }

    @Override
    public void storeToDisk(T item) {
        mLocalSource.create(item);
    }

    @Override
    public void storeToMemory(T item) {
    }
}
