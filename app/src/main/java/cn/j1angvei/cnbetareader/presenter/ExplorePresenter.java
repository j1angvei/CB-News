package cn.j1angvei.cnbetareader.presenter;


import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.contract.ExploreContract;
import cn.j1angvei.cnbetareader.data.repository.ExploreRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.util.ApiUtil;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/7/15.
 */
@PerFragment
public class ExplorePresenter implements ExploreContract.Presenter {
    private static final String TAG = "ExplorePresenter";
    private ExploreContract.View mView;
    private ExploreRepository mRepository;

    @Inject
    public ExplorePresenter(ExploreRepository repository) {
        mRepository = repository;
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
    public void saveMyTopics(List<Topic> topics) {
        Observable.from(topics)
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Topic>() {
                    @Override
                    public void onCompleted() {
                        mView.onAddSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onAddFail();
                    }

                    @Override
                    public void onNext(Topic topic) {
                        mRepository.saveMyTopics(topic);
                    }
                });

    }

    @Override
    public void setView(ExploreContract.View view) {
        mView = view;
    }
}
