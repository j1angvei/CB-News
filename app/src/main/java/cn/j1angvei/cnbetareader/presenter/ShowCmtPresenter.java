package cn.j1angvei.cnbetareader.presenter;

import android.text.TextUtils;
import android.util.Log;

import java.util.Map;

import javax.inject.Inject;

import cn.j1angvei.cnbetareader.bean.Comments;
import cn.j1angvei.cnbetareader.contract.ShowCmtContract;
import cn.j1angvei.cnbetareader.data.remote.api.CnbetaApi;
import cn.j1angvei.cnbetareader.data.remote.response.BaseResponse;
import cn.j1angvei.cnbetareader.data.repository.CommentsRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.util.ApiUtil;
import cn.j1angvei.cnbetareader.util.HeaderUtil;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/7/28.
 * presenter in mvp
 */
@PerFragment
public class ShowCmtPresenter implements ShowCmtContract.Presenter {
    private static final String TAG = "ShowCmtPresenter";
    private final CommentsRepository mRepository;
    private final ApiUtil mApiUtil;
    private final CnbetaApi mApi;
    private ShowCmtContract.View mView;

    @Inject
    public ShowCmtPresenter(CommentsRepository repository, ApiUtil apiUtil, CnbetaApi api) {
        mRepository = repository;
        mApiUtil = apiUtil;
        mApi = api;
    }

    @Override
    public void setView(ShowCmtContract.View view) {
        mView = view;
    }

    @Override
    public void retrieveComments(String sid, String sn) {
        mView.showLoading();
        String referer = HeaderUtil.assembleRefererValue(sid);
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
                        mView.renderComments(comments);
                    }
                });
    }

    @Override
    public void judgeComment(final String action, String sid, final String tid) {
        String referer = HeaderUtil.assembleRefererValue(sid);
        Map<String, String> param = mApiUtil.getJudgeCommentParam(action, sid, tid);
        mApi.judgeComment(referer, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse>() {
                    @Override
                    public void onCompleted() {
                        //operation already done in onNext
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onJudgeFail();
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        Log.d(TAG, "onNext: " + baseResponse);
                        if (TextUtils.equals(baseResponse.getState(), "success")) {
//                            mView.onJudgeSuccess(action, tid);
                            mView.onJudgeSuccess();
                        } else {
                            mView.onJudgeFail();
                        }
                    }
                });
    }
}
