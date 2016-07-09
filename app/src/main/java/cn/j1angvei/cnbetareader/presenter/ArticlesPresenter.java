package cn.j1angvei.cnbetareader.presenter;

import javax.inject.Inject;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.data.DataRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.view.SwipeView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/7/4.
 */
@PerFragment
public class ArticlesPresenter implements SwipePresenter<Article> {
    private SwipeView<Article> mView;
    private DataRepository mRepository;

    @Inject
    public ArticlesPresenter(DataRepository repository) {
        mRepository = repository;
    }

    @Override
    public void setView(SwipeView<Article> swipeView) {
        mView = swipeView;
    }

    @Override
    public void retrieveItem(String type, int page) {
        mView.showLoading();
        mRepository.getArticleFromSource(type, page)
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
                        mView.renderItem(article);
                    }
                });
    }

}
