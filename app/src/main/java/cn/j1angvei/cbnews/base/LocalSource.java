package cn.j1angvei.cbnews.base;

import cn.j1angvei.cbnews.base.DbHelper;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/23.
 * retrieve data from sqLiteDatabase
 */
public abstract class LocalSource<T> {
    public static final int PAGE_MORE = 1;
    protected DbHelper<T> mDbHelper;

    public LocalSource(DbHelper<T> dbHelper) {
        mDbHelper = dbHelper;
    }

    public void create(T item) {
        mDbHelper.create(item);
    }

    public abstract Observable<T> read(int page, String id, String extra);

    public abstract void update(T item);

    public abstract void delete(T item);

}
