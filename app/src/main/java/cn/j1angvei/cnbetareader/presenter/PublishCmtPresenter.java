package cn.j1angvei.cnbetareader.presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;

import cn.j1angvei.cnbetareader.contract.PublishCmtContract;
import cn.j1angvei.cnbetareader.data.remote.api.CnbetaApi;
import cn.j1angvei.cnbetareader.data.remote.response.PublishCommentResponse;
import cn.j1angvei.cnbetareader.di.scope.PerActivity;
import cn.j1angvei.cnbetareader.util.ApiUtil;
import cn.j1angvei.cnbetareader.util.HeaderUtil;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/8/13.
 */
@PerActivity
public class PublishCmtPresenter implements PublishCmtContract.Presenter {
    private final CnbetaApi mApi;
    private final ApiUtil mApiUtil;
    private PublishCmtContract.View mView;

    @Inject
    public PublishCmtPresenter(CnbetaApi api, ApiUtil apiUtil) {
        mApi = api;
        mApiUtil = apiUtil;
    }

    @Override
    public void setView(PublishCmtContract.View view) {
        mView = view;
    }

    @Override
    public void getCaptchaImage(String sid) {
        mView.showLoading();
        final String referer = HeaderUtil.assembleRefererValue(sid);
        Map<String, String> param = mApiUtil.getCaptchaUrlParam();
        mApi.getCaptchaUrl(referer, param)
                .flatMap(new Func1<ResponseBody, Observable<ResponseBody>>() {
                    @Override
                    public Observable<ResponseBody> call(ResponseBody body) {
                        try {
                            String url = ApiUtil.parseCaptchaParamV(body.string());
                            return mApi.getCaptchaImage(referer, url);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .map(new Func1<ResponseBody, Bitmap>() {
                    @Override
                    public Bitmap call(ResponseBody body) {
                        return BitmapFactory.decodeStream(body.byteStream());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onShowCaptchaFail();
                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        mView.showCaptcha(bitmap);
                    }
                });
    }

    @Override
    public void sendComment(String content, String captcha, String sid, final String pid) {
        String refer = HeaderUtil.assembleRefererValue(sid);
        Map<String, String> param = mApiUtil.getPublishCommentParam(content, captcha, sid, pid);
        mApi.publishComment(refer, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PublishCommentResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onSendFail();
                    }

                    @Override
                    public void onNext(PublishCommentResponse response) {
                        mView.showInfo(response.getInfo() + response.getState());
                    }
                });
    }
}
