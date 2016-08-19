package cn.j1angvei.cnbetareader.data.local.helper;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

import static cn.j1angvei.cnbetareader.util.DbUtil.COL_COMMENT_NUM;
import static cn.j1angvei.cnbetareader.util.DbUtil.COL_ID;
import static cn.j1angvei.cnbetareader.util.DbUtil.COL_SOURCE;
import static cn.j1angvei.cnbetareader.util.DbUtil.COL_SUMMARY;
import static cn.j1angvei.cnbetareader.util.DbUtil.COL_THUMB;
import static cn.j1angvei.cnbetareader.util.DbUtil.COL_TIME;
import static cn.j1angvei.cnbetareader.util.DbUtil.COL_TITLE;
import static cn.j1angvei.cnbetareader.util.DbUtil.COL_TOPIC;
import static cn.j1angvei.cnbetareader.util.DbUtil.COL_VIEWER_NUM;
import static cn.j1angvei.cnbetareader.util.DbUtil.TYPE_TEXT;

/**
 * Created by Wayne on 2016/8/14.
 */
@Singleton
public class MyTopicsDbHelper extends SQLiteOpenHelper {
    private static final String TAG = "MyTopicsDbHelper";
    //database
    private static final String DB_NAME = "my_topic.db";
    private static final int DB_VERSION = 1;
    //table relevant
    public static final String TABLE_TOPIC = "my_topic";
    public static final String TABLE_ARTICLE = "topic_article";
    //create table command
    private static final String CREATE_TABLE_TOPIC =
            "CREATE TABLE " + TABLE_TOPIC + "(" +
                    COL_ID + " " + TYPE_TEXT + " PRIMARY KEY," +
                    COL_TITLE + " " + TYPE_TEXT + "," +
                    COL_THUMB + " " + TYPE_TEXT + ")";
    private static final String CREATE_TABLE_ARTICLE =
            "CREATE TABLE " + TABLE_ARTICLE + "(" +
                    COL_ID + " " + TYPE_TEXT + " PRIMARY KEY," +
                    COL_TITLE + " " + TYPE_TEXT + "," +
                    COL_TOPIC + " " + TYPE_TEXT + "," +
                    COL_SUMMARY + " " + TYPE_TEXT + "," +
                    COL_COMMENT_NUM + " " + TYPE_TEXT + "," +
                    COL_VIEWER_NUM + " " + TYPE_TEXT + "," +
                    COL_TIME + " " + TYPE_TEXT + "," +
                    COL_SOURCE + ")";

    @Inject
    public MyTopicsDbHelper(Application context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TOPIC);
        db.execSQL(CREATE_TABLE_ARTICLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
