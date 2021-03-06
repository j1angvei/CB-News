package cn.j1angvei.cbnews.newslist.bookmark;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.base.DbHelper;
import cn.j1angvei.cbnews.bean.Bookmark;
import cn.j1angvei.cbnews.di.qualifier.QBookmark;
import cn.j1angvei.cbnews.util.DateUtil;
import rx.Observable;

/**
 * Created by Wayne on 2016/8/20.
 */
@Singleton
@QBookmark
public class BookmarkDbHelper extends DbHelper<Bookmark> {
    private static final String DB_NAME = "bookmark.db";
    private static final int DB_VERSION = 4;

    @Inject
    public BookmarkDbHelper(Application context) {
        super(context, DB_NAME, null, DB_VERSION);
        mTableName = "bookmark";
        mTableCreate = CREATE_TABLE + BLANK + mTableName + BLANK +
                LEFT_BRACKET +
                COL_SID + BLANK + TYPE_TEXT + BLANK + PRIMARY_KEY + BLANK + COMMA +
                COL_TITLE + BLANK + TYPE_TEXT + COMMA +
                COL_TIME + BLANK + TYPE_TEXT + COMMA +
                COL_SOURCE_TYPE + BLANK + TYPE_TEXT +
                RIGHT_BRACKET;
    }

    @Override
    public void create(Bookmark item) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COL_SID, item.getSid());
            values.put(COL_TITLE, item.getTitle());
            values.put(COL_SOURCE_TYPE, item.getType());
            values.put(COL_TIME, DateUtil.convertDefault(item.getTime()));
            db.insertWithOnConflict(mTableName, null, values, SQLiteDatabase.CONFLICT_IGNORE);
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
                    bookmark.setType(cursor.getString(cursor.getColumnIndex(COL_SOURCE_TYPE)));
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
        return Observable.from(bookmarks);
    }
}
