package cn.j1angvei.cnbetareader.data.local;

import android.app.Application;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Content;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/25.
 */
@Singleton
public class ContentLocalSource extends LocalSource<Content> {
    @Inject
    public ContentLocalSource(Application context) {
        super(context);
    }

    @Override
    Observable<Content> get() {
        return null;
    }

    @Override
    void save(Content item) {

    }
}