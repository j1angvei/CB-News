package cn.j1angvei.cbnews.addcomment;

import android.graphics.Bitmap;

import cn.j1angvei.cbnews.base.BasePresenter;
import cn.j1angvei.cbnews.base.BaseView;

/**
 * Created by Wayne on 2016/8/13.
 */
public interface PublishCmtContract {
    interface View extends BaseView {
        void showCaptcha(Bitmap bitmap);

        void onShowFail();

        void onSendSuccess(String message);

        void onSendFail();
    }

    interface Presenter extends BasePresenter<View> {
        void getCaptchaImage(String sid);

        void sendComment(String content, String captcha, String sid, String pid);
    }
}
