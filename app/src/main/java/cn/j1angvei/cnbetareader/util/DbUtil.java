package cn.j1angvei.cnbetareader.util;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import cn.j1angvei.cnbetareader.bean.Topic;

/**
 * Created by Wayne on 2016/8/14.
 */

public class DbUtil {
    private static final String TAG = "DbUtil";
    //sql data type
    public static final String TYPE_INTEGER = "INTEGER";
    public static final String TYPE_TEXT = "TEXT";
    //table column name
    private static final String COL_ID = "topicId";
    private static final String COL_TITLE = "title";
    private static final String COL_THUMB = "thumb";

    public static void addTopic(Topic topic, String table, SQLiteDatabase db) {
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COL_ID, topic.getId());
            values.put(COL_TITLE, topic.getTitle());
            values.put(COL_THUMB, topic.getCover());
            db.insertOrThrow(table, null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d(TAG, "add: Error while insert Topic");
        } finally {
            db.endTransaction();
        }
    }
}
