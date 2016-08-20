package cn.j1angvei.cnbetareader.data.local.helper;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.j1angvei.cnbetareader.bean.Bookmark;
import cn.j1angvei.cnbetareader.data.local.helper.DbHelper;
import rx.Observable;

/**
 * Created by Wayne on 2016/8/20.
 */

public class BookmarkDbHelper extends SQLiteOpenHelper implements DbHelper<Bookmark> {
    private static final String DB_NAME = "bookmark.db";
    private static final int DB_VERSION = 1;

    public BookmarkDbHelper(Application context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void create(Bookmark item) {

    }

    @Override
    public Observable<Bookmark> read(String query) {
        return null;
    }

    @Override
    public void update(Bookmark item) {

    }

    @Override
    public void delete(Bookmark item) {

    }
}
