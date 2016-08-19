package cn.j1angvei.cnbetareader.data.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.data.local.helper.MyTopicsDbHelper;
import cn.j1angvei.cnbetareader.util.DbUtil;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/23.
 */
@Singleton
public class MyTopicsLocalSource implements LocalSource<Article> {
    private static final String TAG = "MyTopicsLocalSource";
    private MyTopicsDbHelper mHelper;

    @Inject
    public MyTopicsLocalSource(MyTopicsDbHelper helper) {
        mHelper = helper;
    }

    @Override
    public void add(Article item) {
    }

    @Override
    public Observable<Article> query() {
        return null;
    }

    @Override
    public void update(Article item) {

    }

    @Override
    public void delete(Article item) {

    }


    public Observable<List<Topic>> queryMyTopic() {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(MyTopicsDbHelper.TABLE_TOPIC, null, null, null, null, null, null);
        return DbUtil.toTopic(cursor);
    }
}
