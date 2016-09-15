package cn.j1angvei.cbnews.newscontent;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.base.DbHelper;
import cn.j1angvei.cbnews.base.LocalSource;
import cn.j1angvei.cbnews.bean.Content;
import cn.j1angvei.cbnews.di.qualifier.QContent;
import rx.Observable;

import static android.provider.BaseColumns._ID;
import static cn.j1angvei.cbnews.base.DbHelper.BLANK;
import static cn.j1angvei.cbnews.base.DbHelper.LIKE;
import static cn.j1angvei.cbnews.base.DbHelper.QUOTE;
import static cn.j1angvei.cbnews.base.DbHelper.SELECT_FROM;
import static cn.j1angvei.cbnews.base.DbHelper.WHERE;

/**
 * Created by Wayne on 2016/7/25.
 */
@Singleton
public class ContentLocalSource extends LocalSource<Content> {
    @Inject
    public ContentLocalSource(@QContent DbHelper<Content> dbHelper) {
        super(dbHelper);
    }

    @Override
    public Observable<Content> read(int page, @NonNull String id, String extra) {
        String query = SELECT_FROM + BLANK + mDbHelper.getTableName() + BLANK +
                WHERE + BLANK + _ID + BLANK + LIKE + BLANK +
                QUOTE + id + QUOTE;
        return mDbHelper.read(query);
    }

    @Override
    public void update(Content item) {

    }

    @Override
    public void delete(Content item) {

    }

}
