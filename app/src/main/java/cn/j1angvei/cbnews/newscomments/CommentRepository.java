package cn.j1angvei.cbnews.newscomments;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.base.LocalSource;
import cn.j1angvei.cbnews.base.RemoteSource;
import cn.j1angvei.cbnews.base.Repository;
import cn.j1angvei.cbnews.bean.Comments;
import cn.j1angvei.cbnews.di.qualifier.QCmt;
import rx.Observable;

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

}
