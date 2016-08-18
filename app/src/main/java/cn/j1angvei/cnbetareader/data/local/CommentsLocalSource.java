package cn.j1angvei.cnbetareader.data.local;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Comments;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/28.
 */
@Singleton
public class CommentsLocalSource implements LocalSource<Comments> {
    @Inject
    public CommentsLocalSource() {

    }

    @Override
    public void add(Comments item) {

    }

    @Override
    public Observable<Comments> query() {
        return null;
    }

    @Override
    public void update(Comments item) {

    }

    @Override
    public void delete(Comments item) {

    }
}
