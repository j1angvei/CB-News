package cn.j1angvei.cnbetareader.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.data.local.AllTopicsLocalSource;
import cn.j1angvei.cnbetareader.data.remote.AllTopicsRemoteSource;
import cn.j1angvei.cnbetareader.exception.NoNetworkException;
import cn.j1angvei.cnbetareader.util.NetworkUtil;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/24.
 */
@Singleton
public class AllTopicsRepository extends Repository<Topic> {
    private static final String TAG = "AllTopicsRepository";
    private final AllTopicsLocalSource mLocalSource;
    private final AllTopicsRemoteSource mRemoteSource;

    @Inject
    public AllTopicsRepository(AllTopicsLocalSource localSource, AllTopicsRemoteSource remoteSource, NetworkUtil networkUtil) {
        super(networkUtil);
        mLocalSource = localSource;
        mRemoteSource = remoteSource;
    }

    @Override
    public Observable<Topic> getData(final int page, String id, String typeOrSN) {
        return mLocalSource.read(page, id, typeOrSN)
                .onErrorResumeNext(new Func1<Throwable, Observable<Topic>>() {
                    @Override
                    public Observable<Topic> call(Throwable throwable) {
                        if (isConnected()) {
                            return mRemoteSource.loadData(page)
                                    .doOnNext(new Action1<Topic>() {
                                        @Override
                                        public void call(Topic topic) {
                                            storeToDisk(topic);
                                        }
                                    });
                        } else {
                            return Observable.error(new NoNetworkException());
                        }
                    }
                });
    }

    @Override
    public void storeToDisk(Topic item) {
        mLocalSource.create(item);
    }

    @Override
    public void storeToMemory(Topic item) {
    }
}
