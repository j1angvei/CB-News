package cn.j1angvei.cbnews.newscontent;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.base.LocalSource;
import cn.j1angvei.cbnews.base.RemoteSource;
import cn.j1angvei.cbnews.base.Repository;
import cn.j1angvei.cbnews.bean.Content;
import cn.j1angvei.cbnews.di.qualifier.QContent;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/25.
 */
@Singleton
public class ContentRepository extends Repository<Content> {

    @Inject
    public ContentRepository(@QContent LocalSource<Content> localSource, @QContent RemoteSource<Content> remoteSource) {
        super(localSource, remoteSource);
    }

    @Override
    public Observable<Content> getCache(String sid) {
        return filterCache(sid)
                .switchIfEmpty(mLocalSource.read(sid)
                        .doOnNext(new Action1<Content>() {
                            @Override
                            public void call(Content content) {
                                mCache.add(content);
                            }
                        }))
                .switchIfEmpty(super.getCache(sid));
    }

    @Override
    public Observable<Content> getLatest(String sid) {
        return mRemoteSource.getContent(sid)
                .doOnNext(new Action1<Content>() {
                    @Override
                    public void call(Content content) {
                        refreshCache(content);
                    }
                });
    }

    @Override
    protected Observable<Content> filterCache(final String sid) {
        return Observable.from(mCache)
                .filter(new Func1<Content, Boolean>() {
                    @Override
                    public Boolean call(Content content) {
                        return sid.equals(content.getSid());
                    }
                });
    }

    private void refreshCache(final Content content) {
        if (mCache.contains(content)) {
            mCache.remove(content);
        }
        mCache.add(content);
    }

    @Override
    public Observable<Content> download(String sid) {
        return mRemoteSource.getContent(sid)
                .doOnNext(new Action1<Content>() {
                    @Override
                    public void call(Content content) {
                        mLocalSource.create(content);
                    }
                })
                .onErrorResumeNext(super.download(sid));
    }
}
