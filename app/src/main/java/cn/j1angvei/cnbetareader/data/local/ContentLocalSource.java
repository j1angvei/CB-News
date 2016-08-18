package cn.j1angvei.cnbetareader.data.local;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Content;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/25.
 */
@Singleton
public class ContentLocalSource implements LocalSource<Content> {
    @Inject
    public ContentLocalSource() {
    }

    @Override
    public void add(Content item) {

    }

    @Override
    public Observable<Content> query() {
        return null;
    }

    @Override
    public void update(Content item) {

    }

    @Override
    public void delete(Content item) {

    }

}
