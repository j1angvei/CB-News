package cn.j1angvei.cnbetareader.presenter;

import javax.inject.Inject;

import cn.j1angvei.cnbetareader.bean.MyTopic;
import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.contract.TopicNewsContract;
import cn.j1angvei.cnbetareader.data.repository.MyTopicsRepository;
import cn.j1angvei.cnbetareader.di.scope.PerActivity;
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

    @Inject
    public TopicNewsPresenter(MyTopicsRepository repository) {
        mRepository = repository;
    }

    @Override
    public void addToMyTopics(Topic topic) {
        Observable.just(topic)
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
                        mRepository.toDisk(myTopic);
                    }
                });
    }

    @Override
    public void setView(TopicNewsContract.View view) {
        mView = view;
    }
}
