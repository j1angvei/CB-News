package cn.j1angvei.cbnews.data.local;

import android.support.annotation.NonNull;

import cn.j1angvei.cbnews.bean.News;
import cn.j1angvei.cbnews.data.local.helper.DbHelper;
import cn.j1angvei.cbnews.exception.LocalItemNotFoundException;
import cn.j1angvei.cbnews.exception.NoMoreItemException;
import rx.Observable;
import rx.functions.Func1;

import static cn.j1angvei.cbnews.data.local.helper.DbHelper.AND;
import static cn.j1angvei.cbnews.data.local.helper.DbHelper.BLANK;
import static cn.j1angvei.cbnews.data.local.helper.DbHelper.COL_SID;
import static cn.j1angvei.cbnews.data.local.helper.DbHelper.COL_SOURCE_TYPE;
import static cn.j1angvei.cbnews.data.local.helper.DbHelper.DESCEND;
import static cn.j1angvei.cbnews.data.local.helper.DbHelper.LIKE;
import static cn.j1angvei.cbnews.data.local.helper.DbHelper.ORDER_BY;
import static cn.j1angvei.cbnews.data.local.helper.DbHelper.QUOTE;
import static cn.j1angvei.cbnews.data.local.helper.DbHelper.SELECT_FROM;
import static cn.j1angvei.cbnews.data.local.helper.DbHelper.WHERE;

/**
 * Created by Wayne on 2016/7/23.
 * store Article
 */
public class NewsLocalSource<T extends News> implements LocalSource<T> {
    private static final String TAG = "NewsLocalSource";
    private final DbHelper<T> mDbHelper;

    public NewsLocalSource(DbHelper<T> dbHelper) {
        mDbHelper = dbHelper;
    }

    @Override
    public void create(final T item) {
        String query = SELECT_FROM + BLANK + mDbHelper.getTableName() + BLANK +
                WHERE + BLANK + COL_SID + BLANK + LIKE + BLANK + QUOTE + item.getSid() + QUOTE + BLANK +
                AND + BLANK + COL_SOURCE_TYPE + BLANK + LIKE + BLANK + QUOTE + item.getSourceType() + QUOTE;
        mDbHelper.read(query)
                .onErrorReturn(new Func1<Throwable, T>() {
                    @Override
                    public T call(Throwable throwable) {
                        if (throwable instanceof LocalItemNotFoundException) {
                            mDbHelper.create(item);
                        }
                        return null;
                    }
                }).subscribe();
    }

    @Override
    public Observable<T> read(@NonNull Integer page, String id, @NonNull String sourceType) {
        if (page > 1) {
            return Observable.error(new NoMoreItemException());
        }
        String builder = (SELECT_FROM + BLANK) + mDbHelper.getTableName() + BLANK +
                WHERE + BLANK + COL_SOURCE_TYPE + BLANK + LIKE + BLANK + QUOTE + sourceType + QUOTE + BLANK +
                ORDER_BY + BLANK + COL_SID + BLANK + DESCEND;
        return mDbHelper.read(builder);
    }

    @Override
    public void update(T item) {

    }

    @Override
    public void delete(T item) {

    }
}
