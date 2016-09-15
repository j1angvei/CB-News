package cn.j1angvei.cbnews.newscomments;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.bean.Comments;
import cn.j1angvei.cbnews.base.DbHelper;
import cn.j1angvei.cbnews.base.LocalSource;
import cn.j1angvei.cbnews.di.qualifier.QCmt;
import cn.j1angvei.cbnews.exception.NoMoreItemException;
import rx.Observable;

import static android.provider.BaseColumns._ID;
import static cn.j1angvei.cbnews.base.DbHelper.BLANK;
import static cn.j1angvei.cbnews.base.DbHelper.LIKE;
import static cn.j1angvei.cbnews.base.DbHelper.QUOTE;
import static cn.j1angvei.cbnews.base.DbHelper.SELECT_FROM;
import static cn.j1angvei.cbnews.base.DbHelper.WHERE;

/**
 * Created by Wayne on 2016/7/28.
 */
@Singleton
public class CommentLocalSource extends LocalSource<Comments> {
    @Inject
    public CommentLocalSource(@QCmt DbHelper<Comments> dbHelper) {
        super(dbHelper);
    }

    @Override
    public Observable<Comments> read(int page, @NonNull String id, String extra) {
        String query = SELECT_FROM + BLANK + mDbHelper.getTableName() + BLANK +
                WHERE + BLANK + _ID + BLANK + LIKE + BLANK +
                QUOTE + id + QUOTE;
        return mDbHelper.read(query);
    }

    @Override
    public void update(Comments item) {

    }

    @Override
    public void delete(Comments item) {

    }
}
