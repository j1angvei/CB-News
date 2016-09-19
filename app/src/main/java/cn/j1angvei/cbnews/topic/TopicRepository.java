package cn.j1angvei.cbnews.topic;

import android.text.TextUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.base.LocalSource;
import cn.j1angvei.cbnews.base.RemoteSource;
import cn.j1angvei.cbnews.base.Repository;
import cn.j1angvei.cbnews.bean.Topic;
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

    @Inject
    public TopicRepository(@QTopic LocalSource<Topic> localSource, @QTopic RemoteSource<Topic> remoteSource) {
        super(localSource, remoteSource);
        mLocalSource = localSource;
        mRemoteSource = remoteSource;
    }

    @Override
    public Observable<Topic> getCache(final String idOrLetter) {
        return Observable.just(TextUtils.isDigitsOnly(idOrLetter))
                .flatMap(new Func1<Boolean, Observable<Topic>>() {
                    @Override
                    public Observable<Topic> call(Boolean isId) {
                        if (isId) {
                            return filterCache(idOrLetter)
                                    .switchIfEmpty(mLocalSource.read(idOrLetter)
                                            .doOnNext(new Action1<Topic>() {
                                                @Override
                                                public void call(Topic topic) {
                                                    mCache.add(topic);
                                                }
                                            }));
                        } else {
                            return mLocalSource.read(idOrLetter)
                                    .doOnNext(new Action1<Topic>() {
                                        @Override
                                        public void call(Topic topic) {
                                            mCache.add(topic);
                                        }
                                    });
                        }
                    }
                })
                .switchIfEmpty(super.getCache(idOrLetter));
    }

    @Override
    public Observable<Topic> getLatest(String letter) {
        return mRemoteSource.getTopics(letter)
                .doOnNext(new Action1<Topic>() {
                    @Override
                    public void call(Topic topic) {
                        if (!mCache.contains(topic)) {
                            mCache.add(topic);
                        }
                        mLocalSource.create(topic);
                    }
                });
    }

    @Override
    protected Observable<Topic> filterCache(final String sid) {
        return Observable.from(mCache)
                .filter(new Func1<Topic, Boolean>() {
                    @Override
                    public Boolean call(Topic topic) {
                        return sid.equals(topic.getId());
                    }
                });
    }

}
