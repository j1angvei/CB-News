package cn.j1angvei.cbnews.newscomments;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.base.LoadMode;
import cn.j1angvei.cbnews.base.LocalSource;
import cn.j1angvei.cbnews.base.RemoteSource;
import cn.j1angvei.cbnews.base.Repository;
import cn.j1angvei.cbnews.bean.Comments;
import cn.j1angvei.cbnews.di.qualifier.QCmt;
import cn.j1angvei.cbnews.exception.IllegalArgumentsException;
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
    public Observable<Comments> getComments(int mode, final String sid, String sn) {
        switch (mode) {
            case LoadMode.LOAD_CACHE:
                return Observable.from(mCache)
                        .filter(new Func1<Comments, Boolean>() {
                            @Override
                            public Boolean call(Comments comments) {
                                return sid.equals(comments.getSid());
                            }
                        })
                        .switchIfEmpty(mLocalSource.read(sid));
            case LoadMode.LOAD_REFRESH:
                return mRemoteSource.getComment(sid, sn);
            default:
                return Observable.error(new IllegalArgumentsException());
        }
    }
}
