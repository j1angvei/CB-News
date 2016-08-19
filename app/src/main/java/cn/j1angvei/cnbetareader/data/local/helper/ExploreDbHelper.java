package cn.j1angvei.cnbetareader.data.local.helper;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

import static cn.j1angvei.cnbetareader.util.DbUtil.COL_ID;
import static cn.j1angvei.cnbetareader.util.DbUtil.COL_THUMB;
import static cn.j1angvei.cnbetareader.util.DbUtil.COL_TITLE;
import static cn.j1angvei.cnbetareader.util.DbUtil.TYPE_TEXT;

/**
 * Created by Wayne on 2016/8/16.
 */
@Singleton
public class ExploreDbHelper extends SQLiteOpenHelper {
    //database
    private static final String DB_NAME = "explore.db";
    private static final int DB_VERSION = 1;
    //table relevant
    public static final String TABLE_TOPICS = "topics";
    private static final String CREATE_TABLE_TOPICS =
            "CREATE TABLE " + TABLE_TOPICS + "(" +
                    COL_ID + " " + TYPE_TEXT + " PRIMARY KEY," +
                    COL_TITLE + " " + TYPE_TEXT + "," +
                    COL_THUMB + " " + TYPE_TEXT + ")";

    @Inject
    public ExploreDbHelper(Application context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_TOPICS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
