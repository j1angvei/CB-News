package cn.j1angvei.cbnews.topic;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.base.Repository;
import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.base.LocalSource;
import cn.j1angvei.cbnews.base.RemoteSource;
import cn.j1angvei.cbnews.di.qualifier.QTopic;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/24.
 */
@Singleton
public class TopicRepository extends Repository<Topic> {
    private static final String TAG = "TopicRepository";

    @Inject
    public TopicRepository(@QTopic LocalSource<Topic> localSource, @QTopic RemoteSource<Topic> remoteSource) {
        super(localSource, remoteSource);
        mLocalSource = localSource;
        mRemoteSource = remoteSource;
    }


}
