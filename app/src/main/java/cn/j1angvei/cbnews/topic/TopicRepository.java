package cn.j1angvei.cbnews.topic;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.base.LocalSource;
import cn.j1angvei.cbnews.base.RemoteSource;
import cn.j1angvei.cbnews.base.Repository;
import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.di.qualifier.QTopic;
import cn.j1angvei.cbnews.exception.ItemNotFoundException;
import cn.j1angvei.cbnews.util.ApiUtil;
import rx.Observable;

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

    @Override
    public Observable<Topic> getTopic(String id) {
        return mLocalSource.read(id)
                .switchIfEmpty(Observable.<Topic>error(new ItemNotFoundException()));
    }

    @Override
    public Observable<Topic> getTopics(int page) {
        String letter = ApiUtil.pageToLetter(page);
        return mLocalSource.read(letter)
                .switchIfEmpty(mRemoteSource.getTopics(letter));
    }
}
