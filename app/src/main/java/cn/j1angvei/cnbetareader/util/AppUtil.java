package cn.j1angvei.cnbetareader.util;

import android.app.Application;
import android.app.UiModeManager;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatDelegate;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Wayne on 2016/9/3.
 */
@Singleton
public class AppUtil {
    private Application mApplication;
    private NetworkUtil mNetworkUtil;

    @Inject
    public AppUtil(Application application, NetworkUtil networkUtil) {
        mApplication = application;
        mNetworkUtil = networkUtil;
    }

    public int getNightMode() {
        return AppCompatDelegate.getDefaultNightMode();
    }

    public void setNightMode(int mode) {
        switch (mode) {
            case AppCompatDelegate.MODE_NIGHT_AUTO:
            case AppCompatDelegate.MODE_NIGHT_NO:
            case AppCompatDelegate.MODE_NIGHT_YES:
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
                AppCompatDelegate.setDefaultNightMode(mode);
                break;
            default:
                MessageUtil.toast("Wrong Night mode", mApplication);
        }
    }

    public boolean isNightModeOn() {
        int mode = mApplication.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return mode == UiModeManager.MODE_NIGHT_YES;
    }

    public boolean isAutoLoadImage() {
        return !mNetworkUtil.isCellularOn();
    }
}
