package cn.j1angvei.cnbetareader.util;

import android.app.Application;
import android.app.UiModeManager;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.fragment.SettingsFragment;

/**
 * Created by Wayne on 2016/9/3.
 */
@Singleton
public class AppUtil {
    private static final String TAG = "AppUtil";
    private Application mApplication;
    private NetworkUtil mNetworkUtil;
    private PrefsUtil mPrefsUtil;

    @Inject
    public AppUtil(Application application, NetworkUtil networkUtil, PrefsUtil prefsUtil) {
        mApplication = application;
        mNetworkUtil = networkUtil;
        mPrefsUtil = prefsUtil;
    }

    public boolean isCsrfTokenValid() {
        return !TextUtils.isEmpty(mPrefsUtil.readString(PrefsUtil.CSRF_TOKEN));
    }

    public void initCsrfToken(String token) {
        mPrefsUtil.writeString(PrefsUtil.CSRF_TOKEN, token);
    }

    public static int getNightMode() {
        return AppCompatDelegate.getDefaultNightMode();
    }

    public static void setNightMode(int mode) {
        switch (mode) {
            case AppCompatDelegate.MODE_NIGHT_AUTO:
            case AppCompatDelegate.MODE_NIGHT_NO:
            case AppCompatDelegate.MODE_NIGHT_YES:
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
                AppCompatDelegate.setDefaultNightMode(mode);
                break;
            default:
                break;
        }
    }

    public void switchTheme() {
        boolean isNight = mPrefsUtil.readBoolean(SettingsFragment.PREF_NIGHT_MODE);
        AppCompatDelegate.setDefaultNightMode(isNight ? AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES);
        mPrefsUtil.writeBoolean(SettingsFragment.PREF_NIGHT_MODE, !isNight);
    }

    public boolean isNightModeOn() {
        int mode = mApplication.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return mode == UiModeManager.MODE_NIGHT_YES;
    }

    public boolean isAutoLoadImage() {
        return !mNetworkUtil.isCellularOn();
    }
}
