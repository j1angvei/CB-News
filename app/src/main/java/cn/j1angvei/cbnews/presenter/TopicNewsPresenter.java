package cn.j1angvei.cbnews.presenter;

import javax.inject.Inject;

import cn.j1angvei.cbnews.bean.MyTopic;
import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.contract.TopicNewsContract;
import cn.j1angvei.cbnews.converter.MyTopicsConverter;
import cn.j1angvei.cbnews.data.repository.MyTopicsRepository;
import cn.j1angvei.cbnews.di.scope.PerActivity;
import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

/**
 * Created by Wayne on 2016/8/30.
 */
@PerActivity
public class TopicNewsPresenter implements TopicNewsContract.Presenter {
    private TopicNewsContract.View mView;
    private final MyTopicsRepository mRepository;
    private final MyTopicsConverter mConverter;

    @Inject
    public TopicNewsPresenter(MyTopicsRepository repository, MyTopicsConverter converter) {
        mRepository = repository;
        mConverter = converter;
    }

    @Override
    public void addToMyTopics(Topic topic) {
        Observable.just(topic)
                .flatMap(new Func1<Topic, Observable<MyTopic>>() {
                    @Override
                    public Observable<MyTopic> call(Topic topic) {
                        return mConverter.toObservable(topic);
                    }
                })
                .subscribe(new Observer<MyTopic>() {
                    @Override
                    public void onCompleted() {
                        mView.onAddSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onAddFail();
                    }

                    @Override
                    public void onNext(MyTopic myTopic) {
                        mRepository.storeToDisk(myTopic);
                    }
                });
    }

    @Override
    public void setView(TopicNewsContract.View view) {
        mView = view;
    }
}
