package cn.j1angvei.cnbetareader.data.repository;

import java.util.Map;

import rx.Observable;

/**
 * Created by Wayne on 2016/7/23.
 */
public interface Repository<T> {
    Observable<T> getData(String extra, Map<String, String> param);

    void toDisk(T item);

    void toRAM(T item);
}
