package cn.j1angvei.cnbetareader.data.local.helper;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Topic;
import rx.Observable;

/**
 * Created by Wayne on 2016/8/20.
 */
@Singleton
public class TopicDbHelper extends SQLiteOpenHelper implements DbHelper<Topic> {
    private static final String TAG = "TopicDbHelper";
    private static final String DB_NAME = "topic.db";
    private static final int DB_VERSION = 2;
    private static final String SQL_CREATE = CREATE_TABLE + BLANK + TABLE_TOPIC + BLANK +
            LEFT_BRACKET +
            _ID + BLANK + TYPE_TEXT + BLANK + PRIMARY_KEY + COMMA +
            COL_LETTER + BLANK + TYPE_TEXT + COMMA +
            COL_TITLE + BLANK + TYPE_TEXT + COMMA +
            COL_THUMB + BLANK + TYPE_TEXT +
            RIGHT_BRACKET;
    private static final String SQL_DROP = DROP_TABLE + BLANK + TABLE_TOPIC;

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
            values.put(COL_LETTER, item.getLetter());
            values.put(COL_TITLE, item.getTitle());
            values.put(COL_THUMB, item.getThumb());
            db.insertOrThrow(TABLE_TOPIC, null, values);
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
                    topic.setLetter(cursor.getString(cursor.getColumnIndex(COL_LETTER)));
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
}
