package cn.j1angvei.cbnews.newslist;

import android.util.Log;

import java.util.List;

import cn.j1angvei.cbnews.base.LoadMode;
import cn.j1angvei.cbnews.base.Repository;
import cn.j1angvei.cbnews.bean.News;
import cn.j1angvei.cbnews.util.ErrorUtil;
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
    private String mType;

    public NewsPresenter(Repository<N> repository) {
        Log.d(TAG, "NewsPresenter: constructor");
        mRepository = repository;
    }

    @Override
    public void setView(NewsContract.View<N> view) {
        mView = view;
        mType = mView.getType();
    }

    @Override
    public void loadCache() {
        subscribe(LoadMode.LOAD_CACHE);
    }

    @Override
    public void loadRefresh() {
        subscribe(LoadMode.LOAD_REFRESH);
    }

    @Override
    public void loadMore() {
        subscribe(LoadMode.LOAD_MORE);
    }

    @Override
    public void getNews(final int loadMode, String type) {
        mRepository.getNews(loadMode, mType)
                .subscribeOn(Schedulers.io())
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
                        if (loadMode == LoadMode.LOAD_REFRESH) {
                            mView.clearNews();
                        }
                        mView.renderNews(ns);
                    }
                });
    }

    private void subscribe(final int mode) {
        mRepository.getNews(mode, mType)
                .subscribeOn(Schedulers.io())
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
                        if (mode == LoadMode.LOAD_REFRESH) {
                            mView.clearNews();
                        }
                        mView.renderNews(ns);
                    }
                });
    }
}
