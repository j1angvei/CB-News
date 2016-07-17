package cn.j1angvei.cnbetareader.presenter;

import javax.inject.Inject;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.data.DataRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/7/10.
 */
@PerFragment
public class MyTopicsPresenter extends SwipePresenter<Article> {
    @Inject
    public MyTopicsPresenter(DataRepository repository) {
        mRepository = repository;
    }

    @Override
    public void retrieveItem(String type, int page) {
        super.retrieveItem(type, page);
        mRepository.getTopicArticlesFromSource(type, page)
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
