package cn.j1angvei.cnbetareader.newslist.pastheadlines;

import javax.inject.Inject;

import cn.j1angvei.cnbetareader.bean.Headline;
import cn.j1angvei.cnbetareader.data.DataRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/7/5.
 */
@PerFragment
public class HeadlinePresenter implements HeadlinesContract.Presenter {
    private int mPage = 1;
    private HeadlinesContract.View mView;
    private DataRepository mRepository;

    @Inject
    public HeadlinePresenter(DataRepository repository) {
        mRepository = repository;
    }

    @Override
    public void retrieveLatestHeadlines() {
        mPage = 1;
        mView.clearHeadlines();
        retrieveMoreHeadlines();
    }

    @Override
    public void retrieveMoreHeadlines() {
        mView.showLoading();
        mRepository.getHeadlinesFromSource(mView.getSourceType(), mPage++)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Headline>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Headline headline) {
                        mView.addHeadlines(headline);
                    }
                });
    }

    @Override
    public void setView(HeadlinesContract.View view) {
        mView = view;
    }
}
