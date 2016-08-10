package cn.j1angvei.cnbetareader.presenter;

import android.util.Log;

import java.util.Map;

import cn.j1angvei.cnbetareader.contract.NewsContract;
import cn.j1angvei.cnbetareader.data.repository.NewsRepository;
import cn.j1angvei.cnbetareader.util.ApiUtil;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/7/9.
 */
public class NewsPresenter<N> implements NewsContract.Presenter<N> {
    public static final String TAG = "NewsPresenter";
    NewsContract.View<N> mView;
    NewsRepository<N> mRepository;

    public NewsPresenter(NewsRepository<N> repository) {
        mRepository = repository;
    }

    @Override
    public void retrieveNews(String type, int page) {
        mView.showLoading();
        Map<String, String> param = ApiUtil.getNewsParam(type, page);
        mRepository.getData(type, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<N>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onNext(N n) {
                        mView.renderNews(n);
                    }
                });
    }

    @Override
    public void setView(NewsContract.View<N> view) {
        mView = view;
    }
}
