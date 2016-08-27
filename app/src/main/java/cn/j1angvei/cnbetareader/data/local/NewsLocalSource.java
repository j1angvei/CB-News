package cn.j1angvei.cnbetareader.data.local;

import cn.j1angvei.cnbetareader.bean.News;
import cn.j1angvei.cnbetareader.data.local.helper.DbHelper;
import rx.Observable;
import rx.functions.Action1;

import static cn.j1angvei.cnbetareader.data.local.helper.DbHelper.AND;
import static cn.j1angvei.cnbetareader.data.local.helper.DbHelper.BLANK;
import static cn.j1angvei.cnbetareader.data.local.helper.DbHelper.COL_SID;
import static cn.j1angvei.cnbetareader.data.local.helper.DbHelper.COL_SOURCE_TYPE;
import static cn.j1angvei.cnbetareader.data.local.helper.DbHelper.COL_TOPIC;
import static cn.j1angvei.cnbetareader.data.local.helper.DbHelper.LIKE;
import static cn.j1angvei.cnbetareader.data.local.helper.DbHelper.QUOTE;
import static cn.j1angvei.cnbetareader.data.local.helper.DbHelper.SELECT_FROM;
import static cn.j1angvei.cnbetareader.data.local.helper.DbHelper.WHERE;

/**
 * Created by Wayne on 2016/7/23.
 * store Article
 */
public class NewsLocalSource<T extends News> implements LocalSource<T> {
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
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mDbHelper.create(item);
                    }
                })
                .subscribe();
    }

    @Override
    public Observable<T> read(String... args) {
        String sourceType = args[0];
        StringBuilder builder = new StringBuilder();
        builder.append(SELECT_FROM + BLANK)
                .append(mDbHelper.getTableName())
                .append(BLANK + WHERE + BLANK + COL_SOURCE_TYPE + BLANK + LIKE + BLANK + QUOTE)
                .append(sourceType)
                .append(QUOTE);
        if (args.length == 2) {
            String topicId = args[1];
            builder.append(BLANK + AND + BLANK + COL_TOPIC + BLANK + LIKE + BLANK + QUOTE)
                    .append(topicId)
                    .append(QUOTE);
        }
        return mDbHelper.read(builder.toString());
    }

    @Override
    public void update(T item) {

    }

    @Override
    public void delete(T item) {

    }
}
