package cn.j1angvei.cnbetareader.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.data.local.ExploreLocalSource;
import cn.j1angvei.cnbetareader.data.remote.ExploreRemoteSource;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/24.
 */
@Singleton
public class ExploreRepository implements Repository<Topic> {
    private final ExploreLocalSource mLocalSource;
    private final ExploreRemoteSource mRemoteSource;

    @Inject
    public ExploreRepository(ExploreLocalSource localSource, ExploreRemoteSource remoteSource) {
        mLocalSource = localSource;
        mRemoteSource = remoteSource;
    }

    @Override
    public Observable<Topic> get(int page, String... str) {
        return mRemoteSource.getItem(0, str);
    }

    @Override
    public void save(Topic item) {

    }

    @Override
    public void cache(Topic item) {

    }
}
