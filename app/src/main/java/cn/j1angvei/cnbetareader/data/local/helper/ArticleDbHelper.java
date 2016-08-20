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

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.util.DateUtil;
import rx.Observable;

/**
 * Created by Wayne on 2016/8/20.
 */
@Singleton
public class ArticleDbHelper extends SQLiteOpenHelper implements DbHelper<Article> {
    private static final String DB_NAME = "article.db";
    private static final int DB_VERSION = 3;
    private static final String SQL_CREATE = CREATE_TABLE + BLANK + TABLE_ARTICLE + BLANK +
            LEFT_BRACKET +
            _ID + BLANK + TYPE_TEXT + BLANK + PRIMARY_KEY + COMMA +
            COL_TITLE + BLANK + TYPE_TEXT + COMMA +
            COL_TOPIC + BLANK + TYPE_TEXT + COMMA +
            COL_SUMMARY + BLANK + TYPE_TEXT + COMMA +
            COL_COMMENT_NUM + BLANK + TYPE_TEXT + COMMA +
            COL_VIEWER_NUM + BLANK + TYPE_TEXT + COMMA +
            COL_TIME + BLANK + TYPE_TEXT + COMMA +
            COL_THUMB + BLANK + TYPE_TEXT + COMMA +
            COL_SOURCE + BLANK + TYPE_TEXT +
            RIGHT_BRACKET;
    private static final String SQL_DROP = DROP_TABLE + BLANK + TABLE_ARTICLE;

    @Inject
    public ArticleDbHelper(Application context) {
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
    public void create(Article item) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(_ID, item.getSid());
            values.put(COL_TITLE, item.getTitle());
            values.put(COL_TOPIC, item.getTopic());
            values.put(COL_SUMMARY, item.getSummary());
            values.put(COL_COMMENT_NUM, item.getCommentNum());
            values.put(COL_VIEWER_NUM, item.getViewerNum());
            values.put(COL_TIME, DateUtil.convertDefault(item.getTime()));
            values.put(COL_SOURCE, item.getSource());
            values.put(COL_THUMB, item.getThumb());
            db.insertOrThrow(TABLE_ARTICLE, null, values);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public Observable<Article> read(String query) {
        Cursor cursor = getReadableDatabase().rawQuery(query, null);
        List<Article> articles = new ArrayList<>();
        try {
            if (cursor.moveToFirst()) {
                do {
                    Article article = new Article();
                    article.setSid(cursor.getString(cursor.getColumnIndex(_ID)));
                    article.setTitle(cursor.getString(cursor.getColumnIndex(COL_TITLE)));
                    article.setTopic(cursor.getString(cursor.getColumnIndex(COL_TOPIC)));
                    article.setSummary(cursor.getString(cursor.getColumnIndex(COL_SUMMARY)));
                    article.setCommentNum(cursor.getString(cursor.getColumnIndex(COL_COMMENT_NUM)));
                    article.setViewerNum(cursor.getString(cursor.getColumnIndex(COL_VIEWER_NUM)));
                    article.setSource(cursor.getString(cursor.getColumnIndex(COL_SOURCE)));
                    article.setThumb(cursor.getString(cursor.getColumnIndex(COL_THUMB)));
                    articles.add(article);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return Observable.from(articles);
    }

    @Override
    public void update(Article item) {

    }

    @Override
    public void delete(Article item) {

    }
}
