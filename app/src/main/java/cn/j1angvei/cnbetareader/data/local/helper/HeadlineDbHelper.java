package cn.j1angvei.cnbetareader.data.local.helper;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Headline;
import rx.Observable;

/**
 * Created by Wayne on 2016/8/20.
 */
@Singleton
public class HeadlineDbHelper extends SQLiteOpenHelper implements DbHelper<Headline> {
    private static final String DB_NAME = "headline.db";
    private static final int DB_VERSION = 1;

    @Inject
    public HeadlineDbHelper(Application context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void create(Headline item) {

    }

    @Override
    public Observable<Headline> read(String query) {
        return null;
    }

    @Override
    public void update(Headline item) {

    }

    @Override
    public void delete(Headline item) {

    }
}
