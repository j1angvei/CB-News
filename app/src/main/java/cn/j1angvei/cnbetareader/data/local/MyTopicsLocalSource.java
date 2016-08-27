package cn.j1angvei.cnbetareader.data.local;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.MyTopic;
import cn.j1angvei.cnbetareader.data.local.helper.MyTopicsDbHelper;
import rx.Observable;

import static cn.j1angvei.cnbetareader.data.local.helper.DbHelper.ASCEND;
import static cn.j1angvei.cnbetareader.data.local.helper.DbHelper.BLANK;
import static cn.j1angvei.cnbetareader.data.local.helper.DbHelper.COL_ADD_ORDER;
import static cn.j1angvei.cnbetareader.data.local.helper.DbHelper.DESCEND;
import static cn.j1angvei.cnbetareader.data.local.helper.DbHelper.ORDER_BY;
import static cn.j1angvei.cnbetareader.data.local.helper.DbHelper.SELECT_FROM;

/**
 * Created by Wayne on 2016/7/23.
 */
@Singleton
public class MyTopicsLocalSource implements LocalSource<MyTopic> {
    private static final String TAG = "MyTopicsLocalSource";
    private final MyTopicsDbHelper mDbHelper;

    @Inject
    public MyTopicsLocalSource(MyTopicsDbHelper dbHelper) {
        mDbHelper = dbHelper;
    }

    @Override
    public void create(MyTopic item) {
        mDbHelper.create(item);
    }

    @Override
    public Observable<MyTopic> read(String... args) {
        boolean isAscend = Boolean.parseBoolean(args[0]);
        String query = SELECT_FROM + BLANK +
                mDbHelper.getTableName() +
                BLANK + ORDER_BY + BLANK + COL_ADD_ORDER + BLANK + (isAscend ? ASCEND : DESCEND);
        return mDbHelper.read(query);
    }

    @Override
    public void update(MyTopic item) {
        mDbHelper.update(item);
    }

    @Override
    public void delete(MyTopic item) {

    }
}
