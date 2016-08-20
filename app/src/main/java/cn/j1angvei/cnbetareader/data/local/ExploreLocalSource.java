package cn.j1angvei.cnbetareader.data.local;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.data.local.helper.TopicDbHelper;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/24.
 */
@Singleton
public class ExploreLocalSource implements LocalSource<Topic> {
    private final TopicDbHelper mHelper;

    @Inject
    public ExploreLocalSource(TopicDbHelper helper) {
        mHelper = helper;
    }

    @Override
    public void create(Topic item) {
        mHelper.create(item);
    }

    @Override
    public Observable<Topic> read() {
        return null;
    }

    @Override
    public void update(Topic item) {

    }

    @Override
    public void delete(Topic item) {

    }
}
