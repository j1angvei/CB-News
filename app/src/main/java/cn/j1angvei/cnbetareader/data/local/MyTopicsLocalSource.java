package cn.j1angvei.cnbetareader.data.local;

import android.app.Application;
import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Article;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/23.
 */
@Singleton
public class MyTopicsLocalSource extends LocalSource<Article> {

    @Inject
    public MyTopicsLocalSource(Application context) {
        super(context);
    }

    @Override
    Observable<Article> get() {
        return null;
    }

    @Override
    void save(Article item) {

    }
}
