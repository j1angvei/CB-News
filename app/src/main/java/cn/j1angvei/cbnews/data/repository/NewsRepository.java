package cn.j1angvei.cbnews.data.repository;

import cn.j1angvei.cbnews.bean.News;
import cn.j1angvei.cbnews.data.local.LocalSource;
import cn.j1angvei.cbnews.data.remote.RemoteSource;
import cn.j1angvei.cbnews.exception.NeedRefreshException;
import cn.j1angvei.cbnews.exception.RAMItemNotFoundException;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/23.
 * store news like Article Headline Review into SQLiteDatabase
 */
public class NewsRepository<T extends News> extends Repository<T> {
    private static final String TAG = "NewsRepository";

    public NewsRepository(LocalSource<T> localSource, RemoteSource<T> remoteSource) {
        super(localSource, remoteSource);
    }

    /**
     * @param page
     * @param id
     * @param extra
     * @return
     */
    @Override
    Observable<T> fromRAM(int page, String id, String extra) {
        final String type = extra;
        if (page == PAGE_REFRESH) {
            return Observable.error(new NeedRefreshException());
        }
        return Observable.from(mCache)
                .filter(new Func1<T, Boolean>() {
                    @Override
                    public Boolean call(T t) {
                        return t.getType().equals(type);
                    }
                })
                .switchIfEmpty(Observable.<T>error(new RAMItemNotFoundException()));
    }

}
