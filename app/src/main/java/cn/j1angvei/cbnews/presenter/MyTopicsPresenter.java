package cn.j1angvei.cbnews.presenter;

import javax.inject.Inject;

import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.contract.MyTopicsContract;
import cn.j1angvei.cbnews.data.repository.Repository;
import cn.j1angvei.cbnews.di.qualifier.QTopic;
import cn.j1angvei.cbnews.di.scope.PerFragment;
import cn.j1angvei.cbnews.util.AppUtil;

/**
 * Created by Wayne on 2016/8/19.
 */
@PerFragment
public class MyTopicsPresenter implements MyTopicsContract.Presenter {
    private Repository<Topic> mRepository;
    private AppUtil mAppUtil;
    private MyTopicsContract.View mView;

    @Inject
    public MyTopicsPresenter(@QTopic Repository<Topic> repository, AppUtil appUtil) {
        mRepository = repository;
        mAppUtil = appUtil;
    }

    @Override
    public void setView(MyTopicsContract.View view) {
        mView = view;
    }

    @Override
    public void retrieveMyTopics() {
//        mRepository.getDataFromDB(0, null, null)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .toList()
//                .subscribe(new Subscriber<List<MyTopic>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        mView.onMyTopicsEmpty();
//                    }
//
//                    @Override
//                    public void onNext(List<MyTopic> topics) {
//                        mView.renderMyTopics(topics);
//                    }
//                });
    }

}
