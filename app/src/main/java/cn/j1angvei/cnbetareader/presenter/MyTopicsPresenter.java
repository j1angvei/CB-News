package cn.j1angvei.cnbetareader.presenter;

import java.util.List;

import javax.inject.Inject;

import cn.j1angvei.cnbetareader.bean.MyTopic;
import cn.j1angvei.cnbetareader.contract.MyTopicsContract;
import cn.j1angvei.cnbetareader.data.repository.MyTopicsRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.util.PrefsUtil;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/8/19.
 */
@PerFragment
public class MyTopicsPresenter implements MyTopicsContract.Presenter {
    private final MyTopicsRepository mRepository;
    private final PrefsUtil mPrefsUtil;
    private MyTopicsContract.View mView;

    @Inject
    public MyTopicsPresenter(MyTopicsRepository repository, PrefsUtil prefsUtil) {
        mRepository = repository;
        mPrefsUtil = prefsUtil;
    }

    @Override
    public void setView(MyTopicsContract.View view) {
        mView = view;
    }

    @Override
    public void retrieveMyTopics() {
        boolean isAscend = mPrefsUtil.readBoolean(PrefsUtil.KEY_MY_TOPICS_ORDER);
        mRepository.getData(String.valueOf(isAscend), null)
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
