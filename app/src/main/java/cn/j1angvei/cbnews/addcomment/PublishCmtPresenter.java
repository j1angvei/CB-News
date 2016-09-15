package cn.j1angvei.cbnews.addcomment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;

import javax.inject.Inject;

import cn.j1angvei.cbnews.di.scope.PerFragment;
import cn.j1angvei.cbnews.exception.CaptchaFailException;
import cn.j1angvei.cbnews.util.ApiUtil;
import cn.j1angvei.cbnews.util.HeaderUtil;
import cn.j1angvei.cbnews.web.CBApiWrapper;
import cn.j1angvei.cbnews.web.PublishCmtResponse;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Wayne on 2016/8/13.
 */
@PerFragment
public class PublishCmtPresenter implements PublishCmtContract.Presenter {
    private final CBApiWrapper mApiWrapper;
    private PublishCmtContract.View mView;

    @Inject
    public PublishCmtPresenter(CBApiWrapper wrapper) {
        mApiWrapper = wrapper;
    }

    @Override
    public void setView(PublishCmtContract.View view) {
        mView = view;
    }

    @Override
    public void getCaptchaImage(final String sid) {
        mView.showLoading();
        mApiWrapper.getCaptchaVerify(sid)
                .flatMap(new Func1<ResponseBody, Observable<ResponseBody>>() {
                    @Override
                    public Observable<ResponseBody> call(ResponseBody body) {
                        try {
                            String verify = ApiUtil.parseCaptchaVerify(body.string());
                            return mApiWrapper.getCaptchaImage(sid, verify);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return Observable.error(new CaptchaFailException());
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
                        mView.onShowFail();
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
        mApiWrapper.publishComment(refer, captcha, sid, pid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PublishCmtResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onSendFail();
                    }

                    @Override
                    public void onNext(PublishCmtResponse response) {
                        mView.onSendSuccess(response.getInfo());
                    }
                });
    }
}
