package cn.j1angvei.cnbetareader.presenter;

import java.util.List;

import javax.inject.Inject;

import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.contract.MyTopicsContract;
import cn.j1angvei.cnbetareader.data.repository.MyTopicsRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/8/19.
 */
@PerFragment
public class MyTopicsPresenter implements MyTopicsContract.Presenter {
    private MyTopicsContract.View mView;
    private final MyTopicsRepository mRepository;

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
        mRepository.getMyTopic()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Topic>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Topic> topics) {
                        mView.renderMyTopics(topics);
                    }
                })
        ;
    }
}
