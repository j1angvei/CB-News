package cn.j1angvei.cnbetareader.data.local;

import rx.Observable;

/**
 * Created by Wayne on 2016/7/23.
 */
public class NewsLocalSource<T> implements LocalSource<T> {

    public NewsLocalSource() {
    }

    @Override
    public void add(T item) {

    }

    @Override
    public Observable<T> query() {
        return null;
    }

    @Override
    public void update(T item) {

    }

    @Override
    public void delete(T item) {

    }
}
