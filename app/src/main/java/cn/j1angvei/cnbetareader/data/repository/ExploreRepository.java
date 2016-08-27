package cn.j1angvei.cnbetareader.data.repository;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.data.local.ExploreLocalSource;
import cn.j1angvei.cnbetareader.data.remote.ExploreRemoteSource;
import cn.j1angvei.cnbetareader.exception.NoNetworkException;
import cn.j1angvei.cnbetareader.util.NetworkUtil;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/24.
 */
@Singleton
public class ExploreRepository extends Repository<Topic> {
    private static final String TAG = "ExploreRepository";
    private final ExploreLocalSource mLocalSource;
    private final ExploreRemoteSource mRemoteSource;

    @Inject
    public ExploreRepository(ExploreLocalSource localSource, ExploreRemoteSource remoteSource, NetworkUtil networkUtil) {
        super(networkUtil);
        mLocalSource = localSource;
        mRemoteSource = remoteSource;
    }

    /**
     * @param letter index of the topic
     * @param param  should be null
     * @return Topics started with "letter"
     */
    @Override
    public Observable<Topic> getData(final String letter, final Map<String, String> param) {
        return mLocalSource.read(letter)
                .onErrorResumeNext(new Func1<Throwable, Observable<Topic>>() {
                    @Override
                    public Observable<Topic> call(Throwable throwable) {
                        if (isConnected()) {
                            return mRemoteSource.getData(letter, param)
                                    .doOnNext(new Action1<Topic>() {
                                        @Override
                                        public void call(Topic topic) {
                                            toDisk(topic);
                                        }
                                    });
                        } else {
                            return Observable.error(new NoNetworkException());
                        }
                    }
                });
    }

    @Override
    public void toDisk(Topic item) {
        mLocalSource.create(item);
    }

    @Override
    public void toRAM(Topic item) {
    }
}
