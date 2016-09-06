package cn.j1angvei.cbnews.data.repository;

import android.support.annotation.NonNull;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.data.local.AllTopicsLocalSource;
import cn.j1angvei.cbnews.data.remote.AllTopicsRemoteSource;
import cn.j1angvei.cbnews.exception.NoItemInRamException;
import cn.j1angvei.cbnews.exception.NoNetworkException;
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
    private final SparseArray<List<Topic>> mTopicArray;

    @Inject
    public AllTopicsRepository(AllTopicsLocalSource localSource, AllTopicsRemoteSource remoteSource) {
        super(localSource, remoteSource);
        mLocalSource = localSource;
        mRemoteSource = remoteSource;
        mTopicArray = new SparseArray<>();
    }

    @Override
    public Observable<Topic> getDataFromDB(@NonNull final Integer page, String id, String typeOrSN) {
        return Observable.from(mTopicArray.get(page, new ArrayList<Topic>()))
                .switchIfEmpty(mRemoteSource.fetchData(page, null, null))
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Topic>>() {
                    @Override
                    public Observable<? extends Topic> call(Throwable throwable) {
                        if (throwable instanceof NoNetworkException) {
                            return mLocalSource.read(page, null, null);
                        } else {
                            return Observable.error(throwable);
                        }
                    }
                })
                .doOnNext(new Action1<Topic>() {
                    @Override
                    public void call(Topic topic) {
                        storeToMemory(topic);
                        storeToDisk(topic);
                    }
                });
    }

    @Override
    public Observable<Topic> offlineDownload(Integer page, String id, String typeOrSN) {
        return null;
    }

    @Override
    public Observable<Topic> getDataFromRAM(Integer page, String id, String typeOrSN) {
        List<Topic> topics = mTopicArray.get(page);
        if (topics == null) {
            return Observable.error(new NoItemInRamException());
        } else {
            return Observable.from(mTopicArray.get(page));
        }
    }

    @Override
    public void storeToDisk(Topic item) {
        mLocalSource.create(item);
    }

    @Override
    public void storeToMemory(Topic item) {
        List<Topic> topics = mTopicArray.get(item.getPage());
        if (topics == null) {
            topics = new ArrayList<>();
            mTopicArray.setValueAt(item.getPage(), topics);
        } else if (!topics.contains(item)) {
            topics.add(item);
        }

    }
}
