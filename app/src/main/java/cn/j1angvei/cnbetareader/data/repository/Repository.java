package cn.j1angvei.cnbetareader.data.repository;

import rx.Observable;

/**
 * Created by Wayne on 2016/7/23.
 */
public interface Repository<T> {
    Observable<T> get(int page, String... str);

    void toDisk(T item);

    void toRAM(T item);
}
