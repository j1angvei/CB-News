package cn.j1angvei.cnbetareader.presenter;


import javax.inject.Inject;

import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.data.repository.ExploreRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.view.ExploreView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/7/15.
 */
@PerFragment
public class ExplorePresenter implements BasePresenter<ExploreView> {
    ExploreView mView;
    ExploreRepository mRepository;

    @Inject
    public ExplorePresenter(ExploreRepository repository) {
        mRepository = repository;
    }

    public void retrieveTopics(int index) {
        //convert number 1-26 to letter a-z
        String letter = "" + (char) ('a' + index - 1);
        mView.showLoading();
        mRepository.get(0, letter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Topic>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Topic topic) {
                        mView.renderTopic(topic);
                    }
                });
    }

    @Override
    public void setView(ExploreView view) {
        mView = view;
    }
}
