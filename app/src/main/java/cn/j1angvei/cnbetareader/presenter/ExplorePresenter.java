package cn.j1angvei.cnbetareader.presenter;


import android.util.Log;

import java.util.Set;

import javax.inject.Inject;

import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.contract.ExploreContract;
import cn.j1angvei.cnbetareader.data.repository.ExploreRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.util.ApiUtil;
import cn.j1angvei.cnbetareader.util.PrefsUtil;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/7/15.
 */
@PerFragment
public class ExplorePresenter implements ExploreContract.Presenter {
    private static final String TAG = "ExplorePresenter";
    private final ExploreRepository mRepository;
    private final PrefsUtil mPrefsUtil;
    private ExploreContract.View mView;

    @Inject
    public ExplorePresenter(ExploreRepository repository, PrefsUtil prefsUtil) {
        mRepository = repository;
        mPrefsUtil = prefsUtil;
    }

    @Override
    public void retrieveTopics(int page) {
        mView.showLoading();
        String letter = ApiUtil.pageToLetter(page);
        mRepository.getData(letter, null)
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
                    }

                    @Override
                    public void onNext(Topic topic) {
                        mView.renderTopic(topic);
                    }
                });
    }

    @Override
    public void saveMyTopicIds(final Set<String> ids) {
        //As when save prefs set, can not just put new item into retrieved set
        //because it is still the same object, no change will apply to the prefs
        final Set<String> originIds = mPrefsUtil.readStringSet(PrefsUtil.KEY_MY_TOPICS);
        Observable.from(originIds)
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        mPrefsUtil.writeStringSet(PrefsUtil.KEY_MY_TOPICS, ids);
                        mView.onAddSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onAddFail();
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onNext(String s) {
                        ids.add(s);
                    }
                });
    }

    @Override
    public void setView(ExploreContract.View view) {
        mView = view;
    }
}
