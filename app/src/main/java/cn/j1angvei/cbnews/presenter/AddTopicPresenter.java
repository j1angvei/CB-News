package cn.j1angvei.cbnews.presenter;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import cn.j1angvei.cbnews.bean.MyTopic;
import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.contract.AddMyTopicContract;
import cn.j1angvei.cbnews.converter.MyTopicsConverter;
import cn.j1angvei.cbnews.data.repository.AllTopicsRepository;
import cn.j1angvei.cbnews.data.repository.MyTopicsRepository;
import cn.j1angvei.cbnews.di.scope.PerFragment;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/8/28.
 */
@PerFragment
public class AddTopicPresenter implements AddMyTopicContract.Presenter {
    private static final String TAG = "AddTopicPresenter";
    private AddMyTopicContract.View mView;
    private final AllTopicsRepository mAllTopicsRepository;
    private final MyTopicsRepository mRepository;
    private final MyTopicsConverter mConverter;

    @Inject
    public AddTopicPresenter(AllTopicsRepository allTopicsRepository, MyTopicsRepository repository, MyTopicsConverter converter) {
        mAllTopicsRepository = allTopicsRepository;
        mRepository = repository;
        mConverter = converter;
    }

    @Override
    public void retrieveTopics(final int page) {
        mView.showLoading();
        mAllTopicsRepository.getData(page, null, null)
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
                .flatMap(new Func1<Topic, Observable<MyTopic>>() {
                    @Override
                    public Observable<MyTopic> call(Topic topic) {
                        return mConverter.toObservable(topic);
                    }
                })
                .subscribe(new Subscriber<MyTopic>() {
                    @Override
                    public void onCompleted() {
                        mView.onAddMyTopicsSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onNext(MyTopic myTopic) {
                        mRepository.storeToDisk(myTopic);
                    }
                });

    }

    @Override
    public void setView(AddMyTopicContract.View view) {
        mView = view;
    }
}
