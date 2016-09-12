package cn.j1angvei.cbnews.addtopic;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.addtopic.AddTopicContract;
import cn.j1angvei.cbnews.base.Repository;
import cn.j1angvei.cbnews.di.qualifier.QTopic;
import cn.j1angvei.cbnews.di.scope.PerFragment;
import cn.j1angvei.cbnews.util.AppUtil;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/8/28.
 */
@PerFragment
public class AddTopicPresenter implements AddTopicContract.Presenter {
    private static final String TAG = "AddTopicPresenter";
    private AddTopicContract.View mView;
    private final Repository<Topic> mRepository;
    private final AppUtil mAppUtil;

    @Inject
    public AddTopicPresenter(@QTopic Repository<Topic> repository, AppUtil appUtil) {
        mRepository = repository;
        mAppUtil = appUtil;
    }

    @Override
    public void retrieveTopics(final int page) {
        mView.showLoading();
        mRepository.getData(page, null, null)
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Topic>>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onNext(List<Topic> topics) {
                        mView.renderTopics(page, topics);
                    }
                });
    }

    @Override
    public void addToMyTopics(List<Topic> topics) {
        Observable.from(topics)
                .map(new Func1<Topic, String>() {
                    @Override
                    public String call(Topic topic) {
                        return topic.getId();
                    }
                })
                .toList()
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public void onCompleted() {
                        mView.onAddMyTopicsSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onNext(List<String> strings) {
                        mAppUtil.setMyTopics(strings);
                    }
                });


    }

    @Override
    public void setView(AddTopicContract.View view) {
        mView = view;
    }
}
