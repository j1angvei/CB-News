package cn.j1angvei.cnbetareader.presenter;

import android.util.Log;

import java.util.Map;

import javax.inject.Inject;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.contract.MyTopicsContract;
import cn.j1angvei.cnbetareader.data.repository.MyTopicsRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.util.ApiUtil;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/7/10.
 */
@PerFragment
public class MyTopicsPresenter implements MyTopicsContract.Presenter {
    private static final String TAG = "MyTopicsPresenter";
    private MyTopicsContract.View mView;
    private MyTopicsRepository mRepository;

    @Inject
    public MyTopicsPresenter(MyTopicsRepository repository) {
        mRepository = repository;
    }

    @Override
    public void retrieveMyTopics(int page, String topicId) {
        mView.showLoading();
        Map<String, String> param = ApiUtil.getTopicsNewsParam(topicId, page);
        mRepository.getData(null, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Article>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onNext(Article article) {
                        mView.renderArticle(article);
                    }
                });

    }

    @Override
    public void setView(MyTopicsContract.View view) {
        mView = view;
    }
}
