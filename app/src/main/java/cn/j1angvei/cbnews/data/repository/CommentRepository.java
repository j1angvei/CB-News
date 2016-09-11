package cn.j1angvei.cbnews.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.bean.Comments;
import cn.j1angvei.cbnews.data.local.LocalSource;
import cn.j1angvei.cbnews.data.remote.RemoteSource;
import cn.j1angvei.cbnews.di.qualifier.QCmt;
import cn.j1angvei.cbnews.exception.NeedRefreshException;
import cn.j1angvei.cbnews.exception.RAMItemNotFoundException;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/28.
 */
@Singleton
@QCmt
public class CommentRepository extends Repository<Comments> {
    @Inject
    public CommentRepository(@QCmt LocalSource<Comments> localSource, @QCmt RemoteSource<Comments> remoteSource) {
        super(localSource, remoteSource);
    }

    @Override
    Observable<Comments> fromRAM(int page, final String id, String extra) {
        return page == PAGE_REFRESH ?
                Observable.<Comments>error(new NeedRefreshException()) :
                Observable.from(mCache)
                        .filter(new Func1<Comments, Boolean>() {
                            @Override
                            public Boolean call(Comments comments) {
                                return comments.getSid().equals(id);
                            }
                        })
                        .switchIfEmpty(Observable.<Comments>error(new RAMItemNotFoundException()));
    }

}
