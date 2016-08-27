package cn.j1angvei.cnbetareader.data.repository;

import java.util.Map;

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
    public Observable<MyTopic> getData(String isAscend, Map<String, String> param) {
        return mLocalSource.read(isAscend);
    }

    @Override
    void toDisk(MyTopic item) {

    }

    @Override
    void toRAM(MyTopic item) {

    }
}
