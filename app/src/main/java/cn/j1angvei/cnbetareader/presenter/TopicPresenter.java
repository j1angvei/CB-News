package cn.j1angvei.cnbetareader.presenter;


import android.util.Log;

import java.util.Set;

import javax.inject.Inject;

import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.contract.TopicContract;
import cn.j1angvei.cnbetareader.data.repository.TopicRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.util.ApiUtil;
import cn.j1angvei.cnbetareader.util.PrefsUtil;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/7/15.
 */
@PerFragment
public class TopicPresenter implements TopicContract.Presenter {
    private static final String TAG = "TopicPresenter";
    private final TopicRepository mRepository;
    private final PrefsUtil mPrefsUtil;
    private TopicContract.View mView;

    @Inject
    public TopicPresenter(TopicRepository repository, PrefsUtil prefsUtil) {
        mRepository = repository;
        mPrefsUtil = prefsUtil;
    }

    @Override
    public void retrieveTopics(int page) {
        mView.showLoading();
        String letter = ApiUtil.pageToLetter(page);
        mRepository.getData(letter, null)
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
