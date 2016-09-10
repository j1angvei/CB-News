package cn.j1angvei.cbnews.presenter;


import android.util.Log;

import javax.inject.Inject;

import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.contract.AllTopicsContract;
import cn.j1angvei.cbnews.data.repository.Repository;
import cn.j1angvei.cbnews.di.qualifier.QTopic;
import cn.j1angvei.cbnews.di.scope.PerFragment;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/7/15.
 */
@PerFragment
public class AllTopicsPresenter implements AllTopicsContract.Presenter {
    private static final String TAG = "AllTopicsPresenter";
    private final Repository<Topic> mRepository;
    private AllTopicsContract.View mView;

    @Inject
    public AllTopicsPresenter(@QTopic Repository<Topic> repository) {
        mRepository = repository;
    }

    @Override
    public void retrieveTopics(int page) {
        mView.showLoading();
        mRepository.getDataFromDB(page, null, null)
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
