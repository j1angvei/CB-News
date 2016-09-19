package cn.j1angvei.cbnews.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Arrays;

import rx.Observable;

/**
 * Created by Wayne on 2016/8/20.
 * Store common column names, comment methods in {@link android.database.sqlite.SQLiteOpenHelper}
 */

public abstract class DbHelper<T> extends SQLiteOpenHelper implements AllColumns {
    private static final String TAG = "DbHelper";
    protected String mTableName;
    protected String mTableCreate;
    protected String mTableDrop;

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mTableDrop = DROP_TABLE + BLANK + mTableName;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(mTableCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL(mTableDrop);
            onCreate(db);
        }
    }

    public abstract void create(T item);

    public abstract Observable<T> read(String query);

    public void update(T item) {

    }

    public void delete(String selection, String[] args) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            Log.d(TAG, "delete: " + mTableName + " " + selection + " " + Arrays.toString(args));
            db.delete(mTableName, selection, args);
        } finally {
            db.endTransaction();
        }
    }

    public void delete(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            Log.d(TAG, "delete: " + sql);
            db.execSQL(sql);
        } finally {
            db.endTransaction();
        }
    }

    public String getTableName() {
        return mTableName;
    }

}
