package cn.j1angvei.cnbetareader.data.local.helper;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.MyTopic;
import cn.j1angvei.cnbetareader.exception.NoLocalItemException;
import rx.Observable;

/**
 * Created by Wayne on 2016/8/28.
 * store user collected topic ids
 */
@Singleton
public class MyTopicsDbHelper extends SQLiteOpenHelper implements DbHelper<MyTopic> {
    private static final String DB_NAME = "my_topics.db";
    private static final int DB_VERSION = 3;
    private static final String TABLE_NAME = "my_topics";
    private static final String SQL_CREATE = CREATE_TABLE + BLANK + TABLE_NAME + BLANK +
            LEFT_BRACKET +
            _ID + BLANK + TYPE_TEXT + BLANK + PRIMARY_KEY + COMMA +
            COL_LETTER + BLANK + TYPE_TEXT + COMMA +
            COL_TITLE + BLANK + TYPE_TEXT + COMMA +
            COL_THUMB + BLANK + TYPE_TEXT + COMMA +
            COL_ADD_ORDER + BLANK + TYPE_INTEGER +
            RIGHT_BRACKET;
    private static final String SQL_DROP = DROP_TABLE + BLANK + TABLE_NAME;

    @Inject
    public MyTopicsDbHelper(Application context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL(SQL_DROP);
            onCreate(db);
        }
    }

    @Override
    public void create(MyTopic item) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(_ID, item.getId());
            values.put(COL_LETTER, item.getLetter());
            values.put(COL_TITLE, item.getTitle());
            values.put(COL_THUMB, item.getThumb());
            values.put(COL_ADD_ORDER, getRowCount());
            db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private long getRowCount() {
        SQLiteDatabase db = getReadableDatabase();
        long num = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        db.close();
        return num;
    }

    @Override
    public Observable<MyTopic> read(String query) {
        Cursor cursor = getReadableDatabase().rawQuery(query, null);
        List<MyTopic> topics = null;
        try {
            if (cursor.moveToFirst()) {
                topics = new ArrayList<>();
                do {
                    MyTopic topic = new MyTopic();
                    topic.setId(cursor.getString(cursor.getColumnIndex(_ID)));
                    topic.setLetter(cursor.getString(cursor.getColumnIndex(COL_LETTER)));
                    topic.setTitle(cursor.getString(cursor.getColumnIndex(COL_TITLE)));
                    topic.setThumb(cursor.getString(cursor.getColumnIndex(COL_THUMB)));
                    topic.setOrder(cursor.getShort(cursor.getColumnIndex(COL_ADD_ORDER)));
                    topics.add(topic);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        if (topics == null || topics.isEmpty()) {
            return Observable.error(new NoLocalItemException());
        } else {
            return Observable.from(topics);
        }
    }

    @Override
    public void update(MyTopic item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ADD_ORDER, item.getOrder());
        db.update(TABLE_NAME, values, _ID + " = ?", new String[]{item.getId()});
    }

    @Override
    public void delete(MyTopic item) {

    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
