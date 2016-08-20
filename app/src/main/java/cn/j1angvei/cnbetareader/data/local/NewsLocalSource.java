package cn.j1angvei.cnbetareader.data.local;

import cn.j1angvei.cnbetareader.data.local.helper.DbHelper;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/23.
 */
public class NewsLocalSource<T> implements LocalSource<T> {
    private final DbHelper<T> mHelper;

    public NewsLocalSource(DbHelper<T> helper) {
        mHelper = helper;
    }

    @Override
    public void create(T item) {

    }

    @Override
    public Observable<T> read() {
        return null;
    }

    @Override
    public void update(T item) {

    }

    @Override
    public void delete(T item) {

    }
}
