package cn.j1angvei.cbnews.util;

import android.app.Application;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    public void initAppTheme() {
        boolean isNight = mPrefsUtil.readBoolean(SettingsFragment.PREF_NIGHT_MODE, false);
        AppCompatDelegate.setDefaultNightMode(isNight ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }

    public void switchCurrentTheme() {
        boolean isNight = mPrefsUtil.readBoolean(SettingsFragment.PREF_NIGHT_MODE, false);
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

    public boolean isNightModeOn() {
        return AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
    }

    public boolean isAutoLoadImage() {
        return !mNetworkUtil.isNetworkOn() || mNetworkUtil.isWifiOn() || !mPrefsUtil.readBoolean(SettingsFragment.PREF_DATA_SAVE_MODE, true);
    }

    public List<String> getMyTopicIds() {
        String idString = mPrefsUtil.readString(PrefsUtil.MY_TOPIC_IDS);
        List<String> ids = new ArrayList<>();
        if (!idString.isEmpty()) {
            String[] array = idString.split(" ");
            ids = new ArrayList<>(Arrays.asList(array));
        }
        return ids;
    }

    public void setMyTopics(List<String> ids) {
        String idString = TextUtils.join(" ", ids);
        mPrefsUtil.writeString(PrefsUtil.MY_TOPIC_IDS, idString);
    }

}
