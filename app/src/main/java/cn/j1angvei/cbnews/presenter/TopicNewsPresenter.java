package cn.j1angvei.cbnews.presenter;

import javax.inject.Inject;

import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.contract.TopicNewsContract;
import cn.j1angvei.cbnews.di.scope.PerActivity;
import rx.Observable;
import rx.Observer;

/**
 * Created by Wayne on 2016/8/30.
 */
@PerActivity
public class TopicNewsPresenter implements TopicNewsContract.Presenter {
    private TopicNewsContract.View mView;

    @Inject
    public TopicNewsPresenter() {

    }

    @Override
    public void addToMyTopics(Topic topic) {
        Observable.just(topic)
                .subscribe(new Observer<Topic>() {
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
//                        mRepository.toSQL(myTopic);
                    }
                });
    }

    @Override
    public void setView(TopicNewsContract.View view) {
        mView = view;
    }
}
