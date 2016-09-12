package cn.j1angvei.cbnews.newslist;

import android.support.annotation.NonNull;
import android.util.Log;

import cn.j1angvei.cbnews.bean.News;
import cn.j1angvei.cbnews.base.DbHelper;
import cn.j1angvei.cbnews.base.LocalSource;
import cn.j1angvei.cbnews.exception.NoMoreItemException;
import cn.j1angvei.cbnews.exception.SQLItemNotFoundException;
import rx.Observable;
import rx.functions.Func1;

import static cn.j1angvei.cbnews.base.DbHelper.AND;
import static cn.j1angvei.cbnews.base.DbHelper.BLANK;
import static cn.j1angvei.cbnews.base.DbHelper.COL_SID;
import static cn.j1angvei.cbnews.base.DbHelper.COL_SOURCE_TYPE;
import static cn.j1angvei.cbnews.base.DbHelper.DESCEND;
import static cn.j1angvei.cbnews.base.DbHelper.LIKE;
import static cn.j1angvei.cbnews.base.DbHelper.ORDER_BY;
import static cn.j1angvei.cbnews.base.DbHelper.QUOTE;
import static cn.j1angvei.cbnews.base.DbHelper.SELECT_FROM;
import static cn.j1angvei.cbnews.base.DbHelper.WHERE;

/**
 * Created by Wayne on 2016/7/23.
 * store Article
 */
public class NewsLocalSource<T extends News> extends LocalSource<T> {
    private static final String TAG = "NewsLocalSource";

    public NewsLocalSource(DbHelper<T> dbHelper) {
        super(dbHelper);
    }

    @Override
    public void create(final T item) {
        String query = SELECT_FROM + BLANK + mDbHelper.getTableName() + BLANK +
                WHERE + BLANK + COL_SID + BLANK + LIKE + BLANK + QUOTE + item.getSid() + QUOTE + BLANK +
                AND + BLANK + COL_SOURCE_TYPE + BLANK + LIKE + BLANK + QUOTE + item.getType() + QUOTE;
        mDbHelper.read(query)
                .onErrorReturn(new Func1<Throwable, T>() {
                    @Override
                    public T call(Throwable throwable) {
                        if (throwable instanceof SQLItemNotFoundException) {
                            mDbHelper.create(item);
                        }
                        return null;
                    }
                }).subscribe();
    }

    @Override
    public Observable<T> read(int page, String id, @NonNull String extra) {
        Log.d(TAG, "read: page " + page + " id  " + id + " extra " + extra);
        if (page > PAGE_MORE) {
            return Observable.error(new NoMoreItemException());
        }
        String query = (SELECT_FROM + BLANK) + mDbHelper.getTableName() + BLANK +
                WHERE + BLANK + COL_SOURCE_TYPE + BLANK + LIKE + BLANK + QUOTE + extra + QUOTE + BLANK +
                ORDER_BY + BLANK + COL_SID + BLANK + DESCEND;
        Log.d(TAG, "read: " + query);
        return mDbHelper.read(query);
    }

    @Override
    public void update(T item) {

    }

    @Override
    public void delete(T item) {

    }
}
