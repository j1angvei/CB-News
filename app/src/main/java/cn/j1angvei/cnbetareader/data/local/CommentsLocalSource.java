package cn.j1angvei.cnbetareader.data.local;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Comments;
import cn.j1angvei.cnbetareader.data.local.helper.CommentsDbHelper;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/28.
 */
@Singleton
public class CommentsLocalSource implements LocalSource<Comments> {
    private final CommentsDbHelper mHelper;

    @Inject
    public CommentsLocalSource(CommentsDbHelper helper) {
        mHelper = helper;
    }

    @Override
    public void create(Comments item) {
        mHelper.create(item);
    }

    @Override
    public Observable<Comments> read() {
        return null;
    }

    @Override
    public void update(Comments item) {

    }

    @Override
    public void delete(Comments item) {

    }
}
