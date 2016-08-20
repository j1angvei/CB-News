package cn.j1angvei.cnbetareader.data.local.helper;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.j1angvei.cnbetareader.bean.Review;
import cn.j1angvei.cnbetareader.data.local.helper.DbHelper;
import rx.Observable;

/**
 * Created by Wayne on 2016/8/20.
 */

public class ReviewDbHelper extends SQLiteOpenHelper implements DbHelper<Review> {
    private static final String DB_NAME = "review.db";
    private static final int DB_VERSION = 1;

    public ReviewDbHelper(Application context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void create(Review item) {

    }

    @Override
    public Observable<Review> read(String query) {
        return null;
    }

    @Override
    public void update(Review item) {

    }

    @Override
    public void delete(Review item) {

    }
}
