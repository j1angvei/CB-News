package cn.j1angvei.cbnews.data.repository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.bean.MyTopic;
import cn.j1angvei.cbnews.data.local.TopicNewsLocalSource;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Wayne on 2016/7/23.
 * retrieve my topics
 */
@Singleton
public class MyTopicsRepository extends Repository<MyTopic> {
    private List<MyTopic> mMyTopics;

    @Inject
    public MyTopicsRepository(TopicNewsLocalSource localSource) {
        super(localSource, null);
        mLocalSource = localSource;
        mMyTopics = new ArrayList<>();
    }

    @Override
    public Observable<MyTopic> getDataFromDB(Integer page, String id, String typeOrSN) {
        return Observable.from(mMyTopics)
                .switchIfEmpty(mLocalSource.read(null, null, null)
                        .doOnNext(new Action1<MyTopic>() {
                            @Override
                            public void call(MyTopic myTopic) {
                                storeToMemory(myTopic);
                            }
                        }));
    }

    @Override
    public Observable<MyTopic> offlineDownload(Integer page, String id, String typeOrSN) {
        return null;
    }

    @Override
    public Observable<MyTopic> getDataFromRAM(Integer page, String id, String typeOrSN) {
        return null;
    }

    @Override
    public void storeToDisk(MyTopic item) {
        mLocalSource.create(item);
    }

    @Override
    public void storeToMemory(MyTopic item) {
        if (!mMyTopics.contains(item)) {
            mMyTopics.add(item);
        }
    }
}
