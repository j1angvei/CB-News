package cn.j1angvei.cbnews.util;

import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.settings.SettingsFragment;

/**
 * Created by Wayne on 2016/8/10.
 * read and write sharedPreferences
 */
@Singleton
public class PrefsUtil {

    public static final String CSRF_TOKEN = "csrf_token";
    //cookie keywords
    public static final String COOKIE_TOKEN = "cookie_token";
    public static final String COOKIE_SESSION = "cookie_session";
    //my_topics
    public static final String MY_TOPIC_IDS = "my_topic_ids";
    //download
    public static final String DOWNLOAD_PAGES = "download_pages";


    private final SharedPreferences prefs;

    @Inject
    public PrefsUtil(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    public String readString(String key) {
        return prefs.getString(key, "");
    }

    public void writeString(String key, String value) {
        prefs.edit().putString(key, value).apply();
    }

    public Set<String> readStringSet(String key) {
        return prefs.getStringSet(key, new HashSet<String>());
    }

    public void writeStringSet(String key, Set<String> set) {
        prefs.edit().putStringSet(key, set).apply();
    }

    public boolean readBoolean(String key, boolean defaultValue) {
        return prefs.getBoolean(key, defaultValue);
    }

    public void writeBoolean(String key, boolean value) {
        prefs.edit().putBoolean(key, value).apply();
    }

    public int readIntDefault0(String key) {
        return prefs.getInt(key, 0);
    }

    public int readIntDefault1(String key) {
        return prefs.getInt(key, 1);
    }

    public void writeInt(String key, int value) {
        prefs.edit().putInt(key, value).apply();

    }

    public String readCmtTail() {
        return prefs.getString(SettingsFragment.PREF_CMT_TAIL, "---发送自[西贝新闻]");
    }
}
