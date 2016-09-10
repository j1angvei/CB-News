package cn.j1angvei.cbnews.data.repository;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.data.local.LocalSource;
import cn.j1angvei.cbnews.data.remote.RemoteSource;
import cn.j1angvei.cbnews.di.qualifier.QTopic;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/7/24.
 */
@Singleton
public class TopicRepository extends Repository<Topic> {
    private static final String TAG = "TopicRepository";
    private final SparseArray<List<Topic>> mTopicArray = new SparseArray<>();

    @Inject
    public TopicRepository(@QTopic LocalSource<Topic> localSource, @QTopic RemoteSource<Topic> remoteSource) {
        super(localSource, remoteSource);
        mLocalSource = localSource;
        mRemoteSource = remoteSource;
    }

    @Override
    public Observable<Topic> getDataFromDB(@NonNull final Integer page, String id, String typeOrSN) {
        return Observable.from(mTopicArray.get(page, new ArrayList<Topic>()))//from RAM
                .switchIfEmpty(mLocalSource.read(page, null, null))//from local db
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Topic>>() {
                    @Override
                    public Observable<? extends Topic> call(Throwable throwable) {
                        Log.e(TAG, "call: ", throwable);
                        return mRemoteSource.fetchData(page)//from server
                                .doOnNext(new Action1<Topic>() {
                                    @Override
                                    public void call(Topic topic) {
                                        storeToDisk(topic);
                                    }
                                });
                    }
                })
                .doOnNext(new Action1<Topic>() {
                    @Override
                    public void call(Topic topic) {
                        storeToMemory(topic);
                    }
                });
    }

    @Override
    public Observable<Topic> offlineDownload(Integer page, String id, String typeOrSN) {
        return null;
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
