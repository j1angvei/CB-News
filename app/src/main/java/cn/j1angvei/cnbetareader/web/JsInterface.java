package cn.j1angvei.cnbetareader.web;

import android.content.Context;
import android.os.Handler;
import android.webkit.JavascriptInterface;

import cn.j1angvei.cnbetareader.activity.ContentActivity;
import cn.j1angvei.cnbetareader.util.MessageUtil;
import cn.j1angvei.cnbetareader.util.Navigator;

/**
 * Created by Wayne on 2016/9/2.
 */

public class JsInterface {
    private static final String TAG = "JsInterface";
    private Context mContext;

    public JsInterface(Context c) {
        mContext = c;
    }

    @JavascriptInterface
    public boolean isNightMode() {
        return ((ContentActivity) mContext).isNightMode();
    }

    @JavascriptInterface
    public boolean isAutoLoadImage() {
        return ((ContentActivity) mContext).isAutoLoadImage();
    }

    @JavascriptInterface
    public void openImageInActivity(final String pos, final String[] srcs) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                int curPos = Integer.parseInt(pos);
                Navigator.toContentImage(curPos, srcs, mContext);
            }
        });

    }

    @JavascriptInterface
    public void showMessage(String message) {
        MessageUtil.toast(message, mContext);
    }
}