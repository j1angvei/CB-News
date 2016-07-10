package cn.j1angvei.cnbetareader.presenter;

import javax.inject.Inject;

import cn.j1angvei.cnbetareader.bean.Headline;
import cn.j1angvei.cnbetareader.data.DataRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.view.SwipeView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/7/5.
 */
@PerFragment
public class HeadlinePresenter extends SwipePresenter<Headline> {
    private SwipeView<Headline> mView;
    private DataRepository mRepository;

    @Inject
    public HeadlinePresenter(DataRepository repository) {
        mRepository = repository;
    }

    @Override
    public void setView(SwipeView<Headline> swipeView) {
        mView = swipeView;
    }

    @Override
    public void retrieveItem(String type, int page) {
        mView.showLoading();
        mRepository.getHeadlinesFromSource(type, page)
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
                        mView.renderItem(headline);
                    }
                });

    }

}
