package cn.j1angvei.cbnews.newscontent;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.base.DbHelper;
import cn.j1angvei.cbnews.bean.Content;
import cn.j1angvei.cbnews.util.DateUtil;
import rx.Observable;

/**
 * Created by Wayne on 2016/8/20.
 * store all content in {@link SQLiteDatabase}
 */
@Singleton
public class ContentDbHelper extends SQLiteOpenHelper implements DbHelper<Content> {
    private static final String DB_NAME = "content.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "content";
    private static final String SQL_CREATE = CREATE_TABLE + BLANK + TABLE_NAME + BLANK +
            LEFT_BRACKET +
            _ID + BLANK + TYPE_TEXT + BLANK + PRIMARY_KEY + COMMA +
            COL_TITLE + BLANK + TYPE_TEXT + COMMA +
            COL_SUMMARY + BLANK + TYPE_TEXT + COMMA +
            COL_SOURCE + BLANK + TYPE_TEXT + COMMA +
            COL_TIME + BLANK + TYPE_TEXT + COMMA +
            COL_DETAIL + BLANK + TYPE_TEXT + COMMA +
            COL_SN + BLANK + TYPE_TEXT + COMMA +
            COL_THUMB + BLANK + TYPE_TEXT +
            RIGHT_BRACKET;
    private static final String SQL_DROP = DROP_TABLE + BLANK + OR;

    @Inject
    public ContentDbHelper(Application context) {
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
    public void create(Content item) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(_ID, item.getSid());
            values.put(COL_TITLE, item.getTitle());
            values.put(COL_SUMMARY, item.getSummary());
            values.put(COL_SOURCE, item.getSource());
            values.put(COL_TIME, DateUtil.convertDefault(item.getTime()));
            values.put(COL_DETAIL, item.getDetail());
            values.put(COL_SN, item.getSn());
            values.put(COL_THUMB, item.getThumb());
            db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public Observable<Content> read(String query) {
        Cursor cursor = getReadableDatabase().rawQuery(query, null);
        List<Content> contents = new ArrayList<>();
        try {
            if (cursor.moveToFirst()) {
                do {
                    Content content = new Content();
                    content.setSid(cursor.getString(cursor.getColumnIndex(_ID)));
                    content.setTitle(cursor.getString(cursor.getColumnIndex(COL_TITLE)));
                    content.setSummary(cursor.getString(cursor.getColumnIndex(COL_SUMMARY)));
                    content.setSource(cursor.getString(cursor.getColumnIndex(COL_SOURCE)));
                    try {
                        content.setTime(DateUtil.parseDefault(cursor.getString(cursor.getColumnIndex(COL_TIME))));
                    } catch (ParseException e) {
                        content.setTime(new Date());
                    }
                    content.setDetail(cursor.getString(cursor.getColumnIndex(COL_DETAIL)));
                    content.setSn(cursor.getString(cursor.getColumnIndex(COL_SN)));
                    content.setThumb(cursor.getString(cursor.getColumnIndex(COL_THUMB)));
                    contents.add(content);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return Observable.from(contents);
    }

    @Override
    public void update(Content item) {

    }

    @Override
    public void delete(String query) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL(query);
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
