package cn.j1angvei.cnbetareader.presenter;

import javax.inject.Inject;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.data.repository.MyTopicsRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.view.NestedTopicsView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/7/10.
 */
@PerFragment
public class NestedTopicsPresenter implements BasePresenter<NestedTopicsView> {
    private NestedTopicsView mView;
    private MyTopicsRepository mRepository;

    @Inject
    public NestedTopicsPresenter(MyTopicsRepository repository) {
        mRepository = repository;
    }

    @Override
    public void setView(NestedTopicsView view) {
        mView = view;

    }

    public void retrieveMyTopics(int page, String topicId) {
        mView.showLoading();
        mRepository.get(page, topicId)
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
                        mView.renderArticle(article);
                    }
                });

    }
}
