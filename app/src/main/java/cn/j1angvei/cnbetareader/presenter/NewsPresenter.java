package cn.j1angvei.cnbetareader.presenter;

import cn.j1angvei.cnbetareader.data.repository.NewsRepository;
import cn.j1angvei.cnbetareader.view.NewsView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/7/9.
 */
public class NewsPresenter<N> implements BasePresenter<NewsView<N>> {
    NewsView<N> mView;
    NewsRepository<N> mRepository;

    public NewsPresenter(NewsRepository<N> repository) {
        mRepository = repository;
    }

    public void setView(NewsView<N> newsView) {
        mView = newsView;
    }

    public void retrieveNews(String type, int page) {
        mView.showLoading();
        mRepository.get(page, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<N>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(N n) {
                        mView.renderNews(n);
                    }
                });
    }

}
