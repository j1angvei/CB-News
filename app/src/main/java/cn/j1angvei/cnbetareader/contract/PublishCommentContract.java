package cn.j1angvei.cnbetareader.contract;

import android.graphics.Bitmap;

/**
 * Created by Wayne on 2016/8/13.
 */
public interface PublishCommentContract {
    interface View extends BaseView {
        void loadCaptcha(Bitmap bitmap);

        void onLoadFail();
    }

    interface Presenter extends BasePresenter<View> {
        void getCaptchaImage(String sid);
    }
}
