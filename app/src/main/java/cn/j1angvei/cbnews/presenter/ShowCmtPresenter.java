package cn.j1angvei.cbnews.presenter;

import android.text.TextUtils;
import android.util.Log;

import javax.inject.Inject;

import cn.j1angvei.cbnews.bean.Comments;
import cn.j1angvei.cbnews.bean.Content;
import cn.j1angvei.cbnews.contract.CommentContract;
import cn.j1angvei.cbnews.data.remote.api.CBApiWrapper;
import cn.j1angvei.cbnews.data.remote.response.BaseResponse;
import cn.j1angvei.cbnews.data.repository.Repository;
import cn.j1angvei.cbnews.di.qualifier.QCmt;
import cn.j1angvei.cbnews.di.qualifier.QContent;
import cn.j1angvei.cbnews.di.scope.PerFragment;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/7/28.
 * presenter in mvp
 */
@PerFragment
public class ShowCmtPresenter implements CommentContract.Presenter {
    private static final String TAG = "ShowCmtPresenter";
    private final Repository<Comments> mRepository;
    private final Repository<Content> mContentRepository;
    private CommentContract.View mView;
    private CBApiWrapper mApiWrapper;

    @Inject
    public ShowCmtPresenter(@QCmt Repository<Comments> repository, @QContent Repository<Content> contentRepository, CBApiWrapper wrapper) {
        mRepository = repository;
        mContentRepository = contentRepository;
        mApiWrapper = wrapper;
    }

    @Override
    public void setView(CommentContract.View view) {
        mView = view;
    }

    @Override
    public void retrieveComments(final String sid) {
        mView.showLoading();
        mContentRepository.getData(0, sid, null)
                .map(new Func1<Content, String>() {
                    @Override
                    public String call(Content content) {
                        return content.getSn();
                    }
                })
                .flatMap(new Func1<String, Observable<Comments>>() {
                    @Override
                    public Observable<Comments> call(String sn) {
                        return mRepository.getData(0, sid, sn);
                    }
                })
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
        mApiWrapper.judgeComment(action, sid, tid)
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
                            mView.onJudgeSuccess();
                        } else {
                            mView.onJudgeFail();
                        }
                    }
                });
    }
}
