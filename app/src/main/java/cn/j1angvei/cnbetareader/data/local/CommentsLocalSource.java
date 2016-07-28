package cn.j1angvei.cnbetareader.data.local;

import android.app.Application;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Comments;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/28.
 */
@Singleton
public class CommentsLocalSource extends LocalSource<Comments> {
    @Inject
    public CommentsLocalSource(Application context) {
        super(context);
    }

    @Override
    Observable<Comments> get() {
        return null;
    }

    @Override
    void save(Comments item) {

    }
}
