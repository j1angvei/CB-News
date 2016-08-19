package cn.j1angvei.cnbetareader.data.local;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.data.local.helper.ExploreDbHelper;
import cn.j1angvei.cnbetareader.data.local.helper.MyTopicsDbHelper;
import cn.j1angvei.cnbetareader.util.DbUtil;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/24.
 */
@Singleton
public class ExploreLocalSource implements LocalSource<Topic> {
    private final ExploreDbHelper mHelper;
    private final MyTopicsDbHelper mMyTopicsDbHelper;

    @Inject
    public ExploreLocalSource(ExploreDbHelper helper, MyTopicsDbHelper myTopicsDbHelper) {
        mHelper = helper;
        mMyTopicsDbHelper = myTopicsDbHelper;
    }

    @Override
    public void add(Topic item) {

    }

    @Override
    public Observable<Topic> query() {
        return null;
    }

    @Override
    public void update(Topic item) {

    }

    @Override
    public void delete(Topic item) {

    }

    public void addMyTopic(Topic topic) {
        SQLiteDatabase db = mMyTopicsDbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = DbUtil.fromTopic(topic);
            db.insertOrThrow(MyTopicsDbHelper.TABLE_TOPIC, null, values);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
}
