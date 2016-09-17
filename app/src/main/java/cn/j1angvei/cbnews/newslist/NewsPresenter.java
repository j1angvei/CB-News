package cn.j1angvei.cbnews.newslist;

import java.util.List;

import cn.j1angvei.cbnews.base.Repository;
import cn.j1angvei.cbnews.bean.News;
import cn.j1angvei.cbnews.util.ErrorUtil;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/7/9.
 * retrieve news from repository, pass to view
 */
public class NewsPresenter<N extends News> implements NewsContract.Presenter<N> {
    public static final String TAG = "NewsPresenter";
    private NewsContract.View<N> mView;
    private Repository<N> mRepository;

    public NewsPresenter(Repository<N> repository) {
        mRepository = repository;
    }

    @Override
    public void setView(NewsContract.View<N> view) {
        mView = view;
    }

    @Override
    public void cache(String type) {
        subscribe(false, mRepository.getCache(type));
    }

    @Override
    public void refresh(String type) {
        subscribe(true, mRepository.getLatest(type));
    }

    @Override
    public void more(String type) {
        subscribe(false, mRepository.getMore(type));
    }

    private void subscribe(final boolean refresh, Observable<N> observable) {
        mView.showProgress();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toList()
                .subscribe(new Subscriber<List<N>>() {
                    @Override
                    public void onCompleted() {
                        mView.hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideProgress();
                        mView.showInfo(ErrorUtil.getErrorInfo(e));
                    }

                    @Override
                    public void onNext(List<N> ns) {
                        if (refresh) {
                            mView.clearNews();
                        }
                        mView.renderNews(ns);
                    }
                });
    }
}
