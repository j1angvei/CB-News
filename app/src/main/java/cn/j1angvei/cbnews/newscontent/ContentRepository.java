package cn.j1angvei.cbnews.newscontent;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.bean.Content;
import cn.j1angvei.cbnews.base.LocalSource;
import cn.j1angvei.cbnews.base.RemoteSource;
import cn.j1angvei.cbnews.base.Repository;
import cn.j1angvei.cbnews.di.qualifier.QContent;
import cn.j1angvei.cbnews.exception.NeedRefreshException;
import cn.j1angvei.cbnews.exception.RAMItemNotFoundException;
import rx.Observable;
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
    protected Observable<Content> fromRAM(int page, final String id, String extra) {
        if (page == PAGE_REFRESH) {
            return Observable.error(new NeedRefreshException());
        }
        return Observable.from(mCache)
                .filter(new Func1<Content, Boolean>() {
                    @Override
                    public Boolean call(Content content) {
                        return content.getSid().equals(id);
                    }
                })
                .switchIfEmpty(Observable.<Content>error(new RAMItemNotFoundException()));
    }

}
