package cn.j1angvei.cbnews.web;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.webkit.JavascriptInterface;

import javax.inject.Inject;

import cn.j1angvei.cbnews.di.scope.PerFragment;
import cn.j1angvei.cbnews.util.AppUtil;
import cn.j1angvei.cbnews.util.MessageUtil;
import cn.j1angvei.cbnews.util.Navigator;

/**
 * Created by Wayne on 2016/9/2.
 */
@PerFragment
public class JsInterface {
    private static final String TAG = "JsInterface";
    private Activity mActivity;
    private AppUtil mAppUtil;

    @Inject
    public JsInterface(Activity activity, AppUtil appUtil) {
        mActivity = activity;
        mAppUtil = appUtil;
    }

    @JavascriptInterface
    public boolean isNightMode() {
        Log.d(TAG, "isNightMode: " + mAppUtil.isNightModeOn());
        return mAppUtil.isNightModeOn();
    }

    @JavascriptInterface
    public boolean isAutoLoadImage() {
        return mAppUtil.isAutoLoadImage();
    }

    @JavascriptInterface
    public void openImageInActivity(final String pos, final String[] srcs) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                int curPos = Integer.parseInt(pos);
                Navigator.toContentImage(curPos, srcs, mActivity);
            }
        });

    }

    @JavascriptInterface
    public void showMessage(String message) {
        MessageUtil.toast(message, mActivity);
    }
}