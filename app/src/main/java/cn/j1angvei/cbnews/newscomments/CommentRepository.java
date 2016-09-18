package cn.j1angvei.cbnews.newscomments;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.base.LocalSource;
import cn.j1angvei.cbnews.base.RemoteSource;
import cn.j1angvei.cbnews.base.Repository;
import cn.j1angvei.cbnews.bean.Comments;
import cn.j1angvei.cbnews.di.qualifier.QCmt;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
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
    public Observable<Comments> getCache(String sid) {
        return filterCache(sid)
                .switchIfEmpty(mLocalSource.read(sid))
                .switchIfEmpty(super.getCache(sid));
    }

    @Override
    public Observable<Comments> getLatest(String sid, String sn) {
        return mRemoteSource.getComment(sid, sn)
                .doOnNext(new Action1<Comments>() {
                    @Override
                    public void call(Comments comments) {
                        refreshCache(comments);
                    }
                });
    }

    @Override
    protected Observable<Comments> filterCache(final String sid) {
        return Observable.from(mCache)
                .filter(new Func1<Comments, Boolean>() {
                    @Override
                    public Boolean call(Comments comments) {
                        return sid.equals(comments.getSid());
                    }
                });
    }

    private void refreshCache(final Comments comments) {
        filterCache(comments.getSid())
                .doOnNext(new Action1<Comments>() {
                    @Override
                    public void call(Comments comments) {
                        mCache.remove(comments);
                    }
                })
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        mCache.add(comments);
                    }
                })
                .subscribe();
    }
}
