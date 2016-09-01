package cn.j1angvei.cnbetareader.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.MyTopic;
import cn.j1angvei.cnbetareader.data.local.TopicNewsLocalSource;
import cn.j1angvei.cnbetareader.util.NetworkUtil;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/23.
 * retrieve my topics
 */
@Singleton
public class MyTopicsRepository extends Repository<MyTopic> {
    private final TopicNewsLocalSource mLocalSource;

    @Inject
    public MyTopicsRepository(TopicNewsLocalSource localSource, NetworkUtil util) {
        super(util);
        mLocalSource = localSource;
    }

    @Override
    public Observable<MyTopic> getData(int page, String id, String typeOrSN) {
        return mLocalSource.read(0, null, null);
    }

    @Override
    public void storeToDisk(MyTopic item) {
        mLocalSource.create(item);
    }

    @Override
    void storeToMemory(MyTopic item) {

    }
}
