package cn.j1angvei.cnbetareader.data.local;

import rx.Observable;

/**
 * Created by Wayne on 2016/7/23.
 */
public interface LocalSource<T> {

    void create(T item);

    Observable<T> read(String queryParam);

    void update(T item);

    void delete(T item);

}
