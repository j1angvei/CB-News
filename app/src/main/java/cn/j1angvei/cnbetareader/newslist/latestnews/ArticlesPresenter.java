package cn.j1angvei.cnbetareader.newslist.latestnews;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.data.DataRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/7/4.
 */
@PerFragment
public class ArticlesPresenter implements ArticlesContract.Presenter {
    private int mPage = 1;
    private ArticlesContract.View mView;
    private DataRepository mRepository;

    @Inject
    public ArticlesPresenter(DataRepository repository) {
        mRepository = repository;
    }

    @Override
    public void retrieveLatestNews() {
        mPage = 1;
        mView.clearNews();
        retrieveMoreNews();
    }

    @Override
    public void retrieveMoreNews() {
        mView.showLoading();
        mRepository.getArticleFromSource(mView.getSourceType(), mPage++)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Article>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Article article) {
                        mView.addNews(article);
                    }
                });
    }

    @Override
    public void setView(ArticlesContract.View view) {
        mView = view;
    }
}
