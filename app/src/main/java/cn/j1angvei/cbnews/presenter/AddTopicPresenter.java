package cn.j1angvei.cbnews.presenter;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.contract.AddTopicContract;
import cn.j1angvei.cbnews.data.repository.Repository;
import cn.j1angvei.cbnews.di.qualifier.QTopic;
import cn.j1angvei.cbnews.di.scope.PerFragment;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/8/28.
 */
@PerFragment
public class AddTopicPresenter implements AddTopicContract.Presenter {
    private static final String TAG = "AddTopicPresenter";
    private AddTopicContract.View mView;
    private final Repository<Topic> mTopicRepository;
    //    private final MyTopicsRepository mRepository;

    @Inject
    public AddTopicPresenter(@QTopic Repository<Topic> topicRepository) {
        mTopicRepository = topicRepository;
//        mRepository = repository;
    }

    @Override
    public void retrieveTopics(final int page) {
        mView.showLoading();
        mTopicRepository.getDataFromDB(page, null, null)
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
                .subscribe(new Subscriber<Topic>() {
                    @Override
                    public void onCompleted() {
                        mView.onAddMyTopicsSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onNext(Topic myTopic) {
//                        mRepository.storeToDisk(myTopic);
                    }
                });

    }

    @Override
    public void setView(AddTopicContract.View view) {
        mView = view;
    }
}
