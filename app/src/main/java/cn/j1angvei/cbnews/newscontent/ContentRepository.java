package cn.j1angvei.cbnews.newscontent;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.base.LoadMode;
import cn.j1angvei.cbnews.base.LocalSource;
import cn.j1angvei.cbnews.base.RemoteSource;
import cn.j1angvei.cbnews.base.Repository;
import cn.j1angvei.cbnews.bean.Content;
import cn.j1angvei.cbnews.di.qualifier.QContent;
import cn.j1angvei.cbnews.exception.IllegalArgumentsException;
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
    public Observable<Content> getContent(int mode, final String sid) {
        switch (mode) {
            case LoadMode.LOAD_CACHE:
                return Observable.from(mCache)
                        .filter(new Func1<Content, Boolean>() {
                            @Override
                            public Boolean call(Content content) {
                                return sid.equals(content.getSid());
                            }
                        })
                        .switchIfEmpty(mLocalSource.read(sid));
            case LoadMode.LOAD_REFRESH:
                return mRemoteSource.getContent(sid)
                        .doOnNext(new Action1<Content>() {
                            @Override
                            public void call(Content content) {
                                toCache(content);
                            }
                        });
            default:
                return Observable.error(new IllegalArgumentsException());

        }
    }

    @Override
    protected Observable<Content> filterCache(String type) {
        return null;
    }
}
