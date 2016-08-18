package cn.j1angvei.cnbetareader.data.local;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.data.local.helper.TopicDbHelper;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/23.
 */
@Singleton
public class MyTopicsLocalSource implements LocalSource<Article> {
    private TopicDbHelper mDbHelper;


    @Inject
    public MyTopicsLocalSource(TopicDbHelper helper) {
        mDbHelper = helper;
    }

    @Override
    public void add(Article item) {
    }

    @Override
    public Observable<Article> query() {
        return null;
    }

    @Override
    public void update(Article item) {

    }

    @Override
    public void delete(Article item) {

    }

}
