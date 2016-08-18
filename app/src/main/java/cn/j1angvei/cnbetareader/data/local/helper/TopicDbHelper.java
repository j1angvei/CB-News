package cn.j1angvei.cnbetareader.data.local.helper;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.util.DbUtil;

/**
 * Created by Wayne on 2016/8/14.
 */
@Singleton
public class TopicDbHelper extends SQLiteOpenHelper {
    private static final String TAG = "TopicDbHelper";
    private static final String DB_NAME = "topic.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_TOPIC = "my_topic";
    private static final String COL_ID = "topicId";
    private static final String COL_TITLE = "title";
    private static final String COL_COVER = "cover";
    private static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_TOPIC + "(" +
                    COL_ID + " " + DbUtil.TYPE_TEXT + "," +
                    COL_TITLE + " " + DbUtil.TYPE_TEXT + "," +
                    COL_COVER + DbUtil.TYPE_TEXT +
                    ")";
    private static final String PH_QUERY = "query_j1angvei";
    private static final String SQL_QUERY = "SELECT " + PH_QUERY + " FROM " + TABLE_TOPIC;
    private static final String SQL_UPGRADE = "DROP TABLE IF EXISTS " + TABLE_TOPIC;

    @Inject
    public TopicDbHelper(Application context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if (i != i1) {
            db.execSQL(SQL_UPGRADE);
        }
    }
//
//    @Override
//    public void add(Topic item) {
//        SQLiteDatabase db = getWritableDatabase();
//        db.beginTransaction();
//        try {
//            ContentValues values = new ContentValues();
//            values.put(COL_ID, item.getId());
//            values.put(COL_TITLE, item.getTitle());
//            values.put(COL_COVER, item.getCover());
//            db.insertOrThrow(TABLE_TOPIC, null, values);
//            db.setTransactionSuccessful();
//
//        } catch (Exception e) {
//            Log.d(TAG, "add: Error while insert Topic");
//        } finally {
//            db.endTransaction();
//        }
//    }
//
//    @Override
//    public Observable<Topic> query(String id) {
//        SQLiteDatabase db = getReadableDatabase();
//        String sql = SQL_QUERY.replace(PH_QUERY, id);
//        Cursor cursor = db.rawQuery(sql, null);
//        try {
//            if (cursor.moveToFirst()) {
//
//            }
//        } finally {
//            if (cursor != null && !cursor.isClosed()) {
//                cursor.close();
//            }
//        }
//        return null;
//    }

}
