package cn.j1angvei.cnbetareader.data.local;

import cn.j1angvei.cnbetareader.data.local.helper.DbHelper;
import rx.Observable;

import static cn.j1angvei.cnbetareader.data.local.helper.DbHelper.BLANK;
import static cn.j1angvei.cnbetareader.data.local.helper.DbHelper.SELECT_FROM;

/**
 * Created by Wayne on 2016/7/23.
 */
public class NewsLocalSource<T> implements LocalSource<T> {
    private final DbHelper<T> mHelper;

    public NewsLocalSource(DbHelper<T> helper) {
        mHelper = helper;
    }

    @Override
    public void create(T item) {
        mHelper.create(item);
    }

    @Override
    public Observable<T> read() {
        String query = SELECT_FROM + BLANK + mHelper.getTableName() + BLANK;
        return mHelper.read(query);
    }

    @Override
    public void update(T item) {

    }

    @Override
    public void delete(T item) {

    }
}
