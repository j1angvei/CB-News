package cn.j1angvei.cbnews.newslist;

import cn.j1angvei.cbnews.bean.News;
import cn.j1angvei.cbnews.newslist.NewsContract;
import cn.j1angvei.cbnews.base.Repository;
import cn.j1angvei.cbnews.util.ErrorUtil;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/7/9.
 */
public class NewsPresenter<N extends News> implements NewsContract.Presenter<N> {
    public static final String TAG = "NewsPresenter";
    private NewsContract.View<N> mView;
    private Repository<N> mRepository;

    public NewsPresenter(Repository<N> repository) {
        mRepository = repository;
    }

    @Override
    public void retrieveNews(String type, int page) {
        mView.showLoading();
        mRepository.getData(page, null, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<N>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onGetNewsFail(ErrorUtil.getErrorInfo(e));
                        mView.hideLoading();
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