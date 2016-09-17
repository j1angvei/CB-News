package cn.j1angvei.cbnews.base;

import android.text.TextUtils;
import android.util.Log;

import rx.Observable;

import static android.provider.BaseColumns._ID;
import static cn.j1angvei.cbnews.base.DbHelper.BLANK;
import static cn.j1angvei.cbnews.base.DbHelper.COL_SOURCE_TYPE;
import static cn.j1angvei.cbnews.base.DbHelper.DELETE_FROM;
import static cn.j1angvei.cbnews.base.DbHelper.QUOTE;
import static cn.j1angvei.cbnews.base.DbHelper.WHERE;

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

    public void delete(String condition) {
        String query = DELETE_FROM + BLANK + mDbHelper.getTableName();
        if (!condition.equals("*")) {
            if (TextUtils.isDigitsOnly(condition)) {
                query += BLANK + WHERE + BLANK + _ID + BLANK + "=" + BLANK + QUOTE + condition + QUOTE;
            } else {
                query += BLANK + WHERE + BLANK + COL_SOURCE_TYPE + BLANK + "=" + BLANK + QUOTE + condition + QUOTE;
            }
        }
        Log.d(TAG, "delete: " + query);
        mDbHelper.delete(query);
    }

}
