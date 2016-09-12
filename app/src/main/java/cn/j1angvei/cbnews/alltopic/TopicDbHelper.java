package cn.j1angvei.cbnews.alltopic;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.base.DbHelper;
import cn.j1angvei.cbnews.bean.Topic;
import rx.Observable;

/**
 * Created by Wayne on 2016/8/20.
 */
@Singleton
public class TopicDbHelper extends SQLiteOpenHelper implements DbHelper<Topic> {
    private static final String TAG = "TopicDbHelper";
    private static final String DB_NAME = "topic.db";
    private static final int DB_VERSION = 4;
    private static final String TABLE_NAME = "topic";
    private static final String SQL_CREATE = CREATE_TABLE + BLANK + TABLE_NAME + BLANK +
            LEFT_BRACKET +
            _ID + BLANK + TYPE_TEXT + BLANK + PRIMARY_KEY + COMMA +
            COL_PAGE + BLANK + TYPE_INTEGER + COMMA +
            COL_TITLE + BLANK + TYPE_TEXT + COMMA +
            COL_THUMB + BLANK + TYPE_TEXT +
            RIGHT_BRACKET;
    private static final String SQL_DROP = DROP_TABLE + BLANK + TABLE_NAME;

    @Inject
    public TopicDbHelper(Application context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i != i1) {
            sqLiteDatabase.execSQL(SQL_DROP);
            onCreate(sqLiteDatabase);
        }
    }

    @Override
    public void create(Topic item) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(_ID, item.getId());
            values.put(COL_PAGE, item.getPage());
            values.put(COL_TITLE, item.getTitle());
            values.put(COL_THUMB, item.getThumb());
            db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public Observable<Topic> read(String query) {
        Cursor cursor = getReadableDatabase().rawQuery(query, null);
        List<Topic> topics = new ArrayList<>();
        try {
            if (cursor.moveToFirst()) {
                do {
                    Topic topic = new Topic();
                    topic.setId(cursor.getString(cursor.getColumnIndex(_ID)));
                    topic.setPage(cursor.getInt(cursor.getColumnIndex(COL_PAGE)));
                    topic.setTitle(cursor.getString(cursor.getColumnIndex(COL_TITLE)));
                    topic.setThumb(cursor.getString(cursor.getColumnIndex(COL_THUMB)));
                    topics.add(topic);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return Observable.from(topics);
    }

    @Override
    public void update(Topic item) {

    }

    @Override
    public void delete(Topic item) {

    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
