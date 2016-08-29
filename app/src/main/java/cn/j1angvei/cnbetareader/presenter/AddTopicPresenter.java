package cn.j1angvei.cnbetareader.presenter;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import cn.j1angvei.cnbetareader.bean.MyTopic;
import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.contract.AddMyTopicContract;
import cn.j1angvei.cnbetareader.data.repository.AllTopicsRepository;
import cn.j1angvei.cnbetareader.data.repository.MyTopicsRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.util.ApiUtil;
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

    @Inject
    public AddTopicPresenter(AllTopicsRepository allTopicsRepository, MyTopicsRepository repository) {
        mAllTopicsRepository = allTopicsRepository;
        mRepository = repository;
    }

    @Override
    public void retrieveTopics(final int groupPosition) {
        mView.showLoading();
        String letter = ApiUtil.pageToLetter(groupPosition + 1);
        mAllTopicsRepository.getData(letter, null)
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
                        mView.renderTopics(groupPosition, topics);
                    }
                });
    }

    @Override
    public void addToMyTopics(List<Topic> topics) {
        Observable.from(topics)
                .map(new Func1<Topic, MyTopic>() {
                    @Override
                    public MyTopic call(Topic topic) {
                        MyTopic myTopic = new MyTopic();
                        myTopic.setLetter(topic.getLetter());
                        myTopic.setId(topic.getId());
                        myTopic.setThumb(topic.getThumb());
                        myTopic.setTitle(topic.getTitle());
                        return myTopic;
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
                        mRepository.toDisk(myTopic);
                    }
                });

    }

    @Override
    public void setView(AddMyTopicContract.View view) {
        mView = view;
    }
}
