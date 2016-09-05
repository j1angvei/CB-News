package cn.j1angvei.cbnews.presenter;

import java.util.List;

import javax.inject.Inject;

import cn.j1angvei.cbnews.bean.MyTopic;
import cn.j1angvei.cbnews.contract.MyTopicsContract;
import cn.j1angvei.cbnews.data.repository.MyTopicsRepository;
import cn.j1angvei.cbnews.di.scope.PerFragment;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/8/19.
 */
@PerFragment
public class MyTopicsPresenter implements MyTopicsContract.Presenter {
    private final MyTopicsRepository mRepository;
    private MyTopicsContract.View mView;

    @Inject
    public MyTopicsPresenter(MyTopicsRepository repository) {
        mRepository = repository;
    }

    @Override
    public void setView(MyTopicsContract.View view) {
        mView = view;
    }

    @Override
    public void retrieveMyTopics() {
        mRepository.getData(0, null, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toList()
                .subscribe(new Subscriber<List<MyTopic>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onMyTopicsEmpty();
                    }

                    @Override
                    public void onNext(List<MyTopic> topics) {
                        mView.renderMyTopics(topics);
                    }
                });
    }

}
