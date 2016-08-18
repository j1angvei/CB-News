package cn.j1angvei.cnbetareader.data.local;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Topic;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/24.
 */
@Singleton
public class ExploreLocalSource implements LocalSource<Topic> {
    @Inject
    public ExploreLocalSource() {
    }

    @Override
    public void add(Topic item) {

    }

    @Override
    public Observable<Topic> query() {
        return null;
    }

    @Override
    public void update(Topic item) {

    }

    @Override
    public void delete(Topic item) {

    }
}
