package cn.j1angvei.cnbetareader.data.local;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.data.local.helper.ContentDbHelper;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/25.
 */
@Singleton
public class ContentLocalSource implements LocalSource<Content> {
    private final ContentDbHelper mHelper;

    @Inject
    public ContentLocalSource(ContentDbHelper helper) {
        mHelper = helper;
    }

    @Override
    public void create(Content item) {
        mHelper.create(item);
    }

    @Override
    public Observable<Content> read(String... args) {
        return null;
    }

    @Override
    public void update(Content item) {

    }

    @Override
    public void delete(Content item) {

    }

}
