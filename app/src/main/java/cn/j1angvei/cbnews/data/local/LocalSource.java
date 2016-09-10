package cn.j1angvei.cbnews.data.local;

import cn.j1angvei.cbnews.data.local.helper.DbHelper;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/23.
 * retrieve data from sqLiteDatabase
 */
public abstract class LocalSource<T> {
    DbHelper<T> mDbHelper;

    public LocalSource(DbHelper<T> dbHelper) {
        mDbHelper = dbHelper;
    }

    public abstract void create(T item);

    public abstract Observable<T> read(Integer page, String id, String sourceType);

    public abstract void update(T item);

    public abstract void delete(T item);

}
