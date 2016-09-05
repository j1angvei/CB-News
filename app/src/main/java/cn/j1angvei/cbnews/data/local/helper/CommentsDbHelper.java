package cn.j1angvei.cbnews.data.local.helper;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.bean.Comments;
import cn.j1angvei.cbnews.util.DbUtil;
import rx.Observable;

/**
 * Created by Wayne on 2016/8/20.
 */
@Singleton
public class CommentsDbHelper extends SQLiteOpenHelper implements DbHelper<Comments> {
    private static final String DB_NAME = "comments.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "comments";
    private static final String SQL_CREATE = CREATE_TABLE + BLANK + TABLE_NAME + BLANK +
            LEFT_BRACKET +
            _ID + BLANK + TYPE_TEXT + BLANK + PRIMARY_KEY + COMMA +
            COL_TOKEN + BLANK + TYPE_TEXT + COMMA +
            COL_IS_OPEN + BLANK + TYPE_INTEGER + COMMA +
            COL_JOIN_NUM + BLANK + TYPE_TEXT + COMMA +
            COL_COMMENT_NUM + BLANK + TYPE_TEXT + COMMA +
            COL_PAGE + BLANK + TYPE_TEXT + COMMA +
            COL_HOT_TID + BLANK + TYPE_TEXT + COMMA +
            COL_ALL_TID + BLANK + TYPE_TEXT + COMMA +
            COL_COMMENT_MAP + BLANK + TYPE_TEXT +
            RIGHT_BRACKET;
    private static final String SQL_DROP = DROP_TABLE + BLANK + TABLE_NAME;
    private final DbUtil mDbUtil;

    @Inject
    public CommentsDbHelper(Application context, DbUtil dbUtil) {
        super(context, DB_NAME, null, DB_VERSION);
        mDbUtil = dbUtil;
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
    public void create(Comments item) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(_ID, item.getSid());
            values.put(COL_TOKEN, item.getToken());
            values.put(COL_IS_OPEN, item.isOpen() ? 1 : 0);
            values.put(COL_JOIN_NUM, item.getJoinNum());
            values.put(COL_COMMENT_NUM, item.getCommentNum());
            values.put(COL_PAGE, item.getPage());
            values.put(COL_ALL_TID, mDbUtil.convertStringList(item.getAllIds()));
            values.put(COL_HOT_TID, mDbUtil.convertStringList(item.getHotIds()));
            values.put(COL_COMMENT_MAP, mDbUtil.convertCommentMap(item.getCommentMap()));
            db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public Observable<Comments> read(String query) {
        Cursor cursor = getReadableDatabase().rawQuery(query, null);
        List<Comments> commentsList = new ArrayList<>();
        try {
            if (cursor.moveToFirst()) {
                do {
                    Comments comments = new Comments();
                    comments.setSid(cursor.getString(cursor.getColumnIndex(_ID)));
                    comments.setToken(cursor.getString(cursor.getColumnIndex(COL_TOKEN)));
                    comments.setOpen(cursor.getInt(cursor.getColumnIndex(COL_IS_OPEN)) == 1);
                    comments.setJoinNum(cursor.getString(cursor.getColumnIndex(COL_JOIN_NUM)));
                    comments.setCommentNum(cursor.getString(cursor.getColumnIndex(COL_COMMENT_NUM)));
                    comments.setPage(cursor.getString(cursor.getColumnIndex(COL_PAGE)));
                    comments.setAllIds(mDbUtil.parseStringList(cursor.getString(cursor.getColumnIndex(COL_ALL_TID))));
                    comments.setHotIds(mDbUtil.parseStringList(cursor.getString(cursor.getColumnIndex(COL_HOT_TID))));
                    comments.setCommentMap(mDbUtil.parseCommentMap(cursor.getString(cursor.getColumnIndex(COL_COMMENT_MAP))));
                    commentsList.add(comments);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return Observable.from(commentsList);
    }

    @Override
    public void update(Comments item) {

    }

    @Override
    public void delete(Comments item) {

    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
