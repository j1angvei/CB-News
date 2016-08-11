package cn.j1angvei.cnbetareader.presenter;

import android.util.Log;

import java.util.Map;

import javax.inject.Inject;

import cn.j1angvei.cnbetareader.bean.Comments;
import cn.j1angvei.cnbetareader.contract.CommentsContract;
import cn.j1angvei.cnbetareader.data.repository.CommentsRepository;
import cn.j1angvei.cnbetareader.di.scope.PerActivity;
import cn.j1angvei.cnbetareader.util.ApiUtil;
import cn.j1angvei.cnbetareader.util.HeaderUtil;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/7/28.
 * presenter in mvp
 */
@PerActivity
public class CommentsPresenter implements CommentsContract.Presenter {
    private static final String TAG = "CommentsPresenter";
    private final CommentsRepository mRepository;
    private CommentsContract.View mView;
    private final ApiUtil mApiUtil;

    @Inject
    public CommentsPresenter(CommentsRepository repository, ApiUtil apiUtil) {
        mRepository = repository;
        mApiUtil = apiUtil;
    }

    @Override
    public void setView(CommentsContract.View view) {
        mView = view;
    }

    @Override
    public void retrieveComments(String sid, String sn) {
        mView.showLoading();
        String referer = HeaderUtil.getRefererValue(sid);
        Map<String, String> param = mApiUtil.getCommentsParam(sid, sn);
        mRepository.getData(referer, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Comments>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onNext(Comments comments) {
                        mView.showComments(comments);
                    }
                });
    }
}
