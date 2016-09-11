package cn.j1angvei.cbnews.data.local;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.bean.Comments;
import cn.j1angvei.cbnews.data.local.helper.DbHelper;
import cn.j1angvei.cbnews.di.qualifier.QCmt;
import cn.j1angvei.cbnews.exception.NoMoreItemException;
import rx.Observable;

import static android.provider.BaseColumns._ID;
import static cn.j1angvei.cbnews.data.local.helper.DbHelper.BLANK;
import static cn.j1angvei.cbnews.data.local.helper.DbHelper.LIKE;
import static cn.j1angvei.cbnews.data.local.helper.DbHelper.QUOTE;
import static cn.j1angvei.cbnews.data.local.helper.DbHelper.SELECT_FROM;
import static cn.j1angvei.cbnews.data.local.helper.DbHelper.WHERE;

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
        if (page > PAGE_MORE) {
            return Observable.error(new NoMoreItemException());
        }
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
