package cn.j1angvei.cbnews.topic;


import android.util.Log;

import javax.inject.Inject;

import cn.j1angvei.cbnews.base.Repository;
import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.di.qualifier.QTopic;
import cn.j1angvei.cbnews.di.scope.PerFragment;
import cn.j1angvei.cbnews.util.ApiUtil;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/7/15.
 */
@PerFragment
public class TopicPresenter implements TopicContract.Presenter {
    private static final String TAG = "TopicPresenter";
    private final Repository<Topic> mRepository;
    private TopicContract.View mView;

    @Inject
    public TopicPresenter(@QTopic Repository<Topic> repository) {
        mRepository = repository;
    }

    @Override
    public void retrieveTopics(int page) {
        mView.showLoading();
        String letter = ApiUtil.pageToLetter(page);
        mRepository.getLatest(letter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Topic>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Topic topic) {
                        mView.renderTopic(topic);
                    }
                });
    }

    @Override
    public void setView(TopicContract.View view) {
        mView = view;
    }
}
