package cn.j1angvei.cnbetareader.contract;

import android.graphics.Bitmap;

/**
 * Created by Wayne on 2016/8/13.
 */
public interface PublishCommentContract {
    interface View extends BaseView {
        void showCaptcha(Bitmap bitmap);

        void onShowCaptchaFail();

        void showInfo(String message);

        void onSendFail();

    }

    interface Presenter extends BasePresenter<View> {
        void getCaptchaImage(String sid);

        void sendComment(String content, String captcha, String sid, String pid);
    }
}
