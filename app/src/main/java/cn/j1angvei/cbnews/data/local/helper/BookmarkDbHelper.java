package cn.j1angvei.cbnews.data.local.helper;

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

import cn.j1angvei.cbnews.bean.Bookmark;
import cn.j1angvei.cbnews.exception.NoLocalItemException;
import cn.j1angvei.cbnews.util.DateUtil;
import rx.Observable;

/**
 * Created by Wayne on 2016/8/20.
 */
@Singleton
public class BookmarkDbHelper extends SQLiteOpenHelper implements DbHelper<Bookmark> {
    private static final String DB_NAME = "bookmark.db";
    private static final int DB_VERSION = 3;
    private static final String TABLE_NAME = "bookmark";
    private static final String SQL_CREATE = CREATE_TABLE + BLANK + TABLE_NAME + BLANK +
            LEFT_BRACKET +
            COL_SID + BLANK + TYPE_TEXT + BLANK + PRIMARY_KEY + BLANK + COMMA +
            COL_TITLE + BLANK + TYPE_TEXT + COMMA +
            COL_TIME + BLANK + TYPE_TEXT +
            RIGHT_BRACKET;
    private static final String SQL_DROP = DROP_TABLE + BLANK + TABLE_NAME;

    @Inject
    public BookmarkDbHelper(Application context) {
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
    public void create(Bookmark item) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COL_SID, item.getSid());
            values.put(COL_TITLE, item.getTitle());
            values.put(COL_SOURCE_TYPE, item.getSourceType());
            values.put(COL_TIME, DateUtil.convertDefault(item.getTime()));
            db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public Observable<Bookmark> read(String query) {
        Cursor cursor = getReadableDatabase().rawQuery(query, null);
        List<Bookmark> bookmarks = new ArrayList<>();
        try {
            if (cursor.moveToFirst()) {
                do {
                    Bookmark bookmark = new Bookmark();
                    bookmark.setSid(cursor.getString(cursor.getColumnIndex(COL_SID)));
                    bookmark.setTitle(cursor.getString(cursor.getColumnIndex(COL_TITLE)));
                    bookmark.setSourceType(cursor.getString(cursor.getColumnIndex(COL_SOURCE_TYPE)));
                    try {
                        bookmark.setTime(DateUtil.parseDefault(cursor.getString(cursor.getColumnIndex(COL_TIME))));
                    } catch (ParseException e) {
                        bookmark.setTime(new Date());
                    }
                    bookmarks.add(bookmark);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        if (bookmarks.isEmpty())
            return Observable.error(new NoLocalItemException());
        else
            return Observable.from(bookmarks);
    }

    @Override
    public void update(Bookmark item) {

    }

    @Override
    public void delete(Bookmark item) {

    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}