package cn.j1angvei.cbnews.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.data.local.LocalSource;
import cn.j1angvei.cbnews.data.remote.RemoteSource;
import cn.j1angvei.cbnews.di.qualifier.QTopic;
import cn.j1angvei.cbnews.exception.NeedRefreshException;
import cn.j1angvei.cbnews.exception.RAMItemNotFoundException;
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

    /**
     * One of page, id should be null.
     * If page not null, it is in All Topic module.
     * If id not null, it is in My Topic module
     *
     * @param page  if page not null, retrieve data from SQL or WEB (RAM data may not complete, return error)
     * @param id    if id not null, try to retrieve it from RAM first
     * @param extra no extra info needed in Topic
     * @return topics in RAM
     */

    @Override
    Observable<Topic> fromRAM(int page, final String id, String extra) {
        return id == null ?
                Observable.<Topic>error(new NeedRefreshException()) :
                Observable.from(mCache)
                        .filter(new Func1<Topic, Boolean>() {
                            @Override
                            public Boolean call(Topic topic) {
                                return topic.getId().equals(id);
                            }
                        })
                        .switchIfEmpty(Observable.<Topic>error(new RAMItemNotFoundException()));
    }

}
