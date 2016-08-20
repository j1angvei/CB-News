package cn.j1angvei.cnbetareader.data.local.helper;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Article;
import rx.Observable;

/**
 * Created by Wayne on 2016/8/20.
 */
@Singleton
public class ArticleDbHelper extends SQLiteOpenHelper implements DbHelper<Article> {
    private static final String DB_NAME = "article.db";
    private static final int DB_VERSION = 1;

    @Inject
    public ArticleDbHelper(Application context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public ArticleDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void create(Article item) {

    }

    @Override
    public Observable<Article> read(String query) {
        return null;
    }

    @Override
    public void update(Article item) {

    }

    @Override
    public void delete(Article item) {

    }
}
