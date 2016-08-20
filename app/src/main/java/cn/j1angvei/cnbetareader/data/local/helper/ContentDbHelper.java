package cn.j1angvei.cnbetareader.data.local.helper;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Wayne on 2016/8/20.
 */

public class ContentDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "content.db";
    private static final int DB_VERSION = 1;

    public ContentDbHelper(Application context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}