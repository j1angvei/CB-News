package cn.j1angvei.cnbetareader.presenter;


import android.util.Log;

import javax.inject.Inject;

import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.contract.AllTopicsContract;
import cn.j1angvei.cnbetareader.data.repository.AllTopicsRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/7/15.
 */
@PerFragment
public class AllTopicsPresenter implements AllTopicsContract.Presenter {
    private static final String TAG = "AllTopicsPresenter";
    private final AllTopicsRepository mRepository;
    private AllTopicsContract.View mView;

    @Inject
    public AllTopicsPresenter(AllTopicsRepository repository) {
        mRepository = repository;
    }

    @Override
    public void retrieveTopics(int page) {
        mView.showLoading();
        mRepository.getData(page, null, null)
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
    public void setView(AllTopicsContract.View view) {
        mView = view;
    }
}
