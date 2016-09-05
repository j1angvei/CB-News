package cn.j1angvei.cbnews.util;

import android.app.Application;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;

import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.fragment.SettingsFragment;

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

    public void initAppTheme() {
        boolean isNight = mPrefsUtil.readBoolean(SettingsFragment.PREF_NIGHT_MODE);
        AppCompatDelegate.setDefaultNightMode(isNight ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }

    public void switchCurrentTheme() {
        boolean isNight = mPrefsUtil.readBoolean(SettingsFragment.PREF_NIGHT_MODE);
        AppCompatDelegate.setDefaultNightMode(isNight ? AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES);
        mPrefsUtil.writeBoolean(SettingsFragment.PREF_NIGHT_MODE, !isNight);
    }

    public void initLocaleAsChina() {
        Locale.setDefault(Locale.CHINA);
        Configuration configuration = new Configuration();
        configuration.locale = Locale.CHINA;
        mApplication.getResources().updateConfiguration(configuration, null);
    }

    public static void killProcess() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    private int getDefaultNightMode() {
        return AppCompatDelegate.getDefaultNightMode();
    }

    public boolean isNightModeOn() {
        return getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
    }

    public boolean isAutoLoadImage() {
        return !mNetworkUtil.isCellularOn();
    }

}
