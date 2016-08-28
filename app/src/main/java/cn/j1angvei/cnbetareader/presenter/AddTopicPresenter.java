package cn.j1angvei.cnbetareader.presenter;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.contract.AddTopicContract;
import cn.j1angvei.cnbetareader.data.repository.TopicRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.util.ApiUtil;
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
    private final TopicRepository mRepository;

    @Inject
    public AddTopicPresenter(TopicRepository repository) {
        mRepository = repository;
    }

    @Override
    public void retrieveTopics(final int groupPosition) {
        mView.showLoading();
        String letter = ApiUtil.pageToLetter(groupPosition + 1);
        mRepository.getData(letter, null)
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
    public void setView(AddTopicContract.View view) {
        mView = view;
    }
}
