package cn.j1angvei.cbnews.base;

import android.util.Log;

import java.util.Arrays;

import rx.Observable;

import static cn.j1angvei.cbnews.base.AllColumns.BLANK;
import static cn.j1angvei.cbnews.base.AllColumns.DELETE_FROM;
import static cn.j1angvei.cbnews.base.AllColumns.LIKE;

/**
 * Created by Wayne on 2016/7/23.
 * retrieve data from sqLiteDatabase
 */
public abstract class LocalSource<T> {
    private static final String TAG = "LocalSource";
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

    public void delete(String id) {
        if (id.equals("*")) {
            String sql = DELETE_FROM + BLANK + mDbHelper.getTableName();
            Log.d(TAG, "delete: * " + sql);
            mDbHelper.delete(sql);
        } else {
            String selection = AllColumns._ID + BLANK + LIKE + BLANK + "?";
            String[] args = {id};
            Log.d(TAG, "delete: " + selection + " " + Arrays.toString(args));
            mDbHelper.delete(selection, args);
        }
    }


}
