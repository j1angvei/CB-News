package cn.j1angvei.cbnews.base;

import rx.Observable;

/**
 * Created by Wayne on 2016/7/23.
 * retrieve data from sqLiteDatabase
 */
public abstract class LocalSource<T> {
    protected DbHelper<T> mDbHelper;

    public LocalSource(DbHelper<T> dbHelper) {
        mDbHelper = dbHelper;
    }

    public void create(T item) {
        mDbHelper.create(item);
    }

    public abstract Observable<T> read(String param);//id or type

    public void update(T item) {
        mDbHelper.update(item);
    }

    public abstract void delete(T item);

}
