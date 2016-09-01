package cn.j1angvei.cnbetareader.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.MyTopic;
import cn.j1angvei.cnbetareader.data.local.MyTopicsLocalSource;
import cn.j1angvei.cnbetareader.util.NetworkUtil;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/23.
 * retrieve my topics
 */
@Singleton
public class MyTopicsRepository extends Repository<MyTopic> {
    private final MyTopicsLocalSource mLocalSource;

    @Inject
    public MyTopicsRepository(MyTopicsLocalSource localSource, NetworkUtil util) {
        super(util);
        mLocalSource = localSource;
    }

    @Override
    public Observable<MyTopic> getData(String id, String param, int page) {// no param is needed
        return mLocalSource.read(null, null, 0);
    }

    @Override
    public void storeToDisk(MyTopic item) {
        mLocalSource.create(item);
    }

    @Override
    void storeToMemory(MyTopic item) {

    }
}
