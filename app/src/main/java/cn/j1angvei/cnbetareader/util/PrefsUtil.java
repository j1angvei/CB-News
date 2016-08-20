package cn.j1angvei.cnbetareader.util;

import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Wayne on 2016/8/10.
 * read and write sharedPreferences
 */
@Singleton
public class PrefsUtil {
    //cookie keywords
    public static final String CSRF_TOKEN = "csrf_token";
    public static final String COOKIE_TOKEN = "cookie_token";
    public static final String COOKIE_SESSION = "cookie_session";
    //my_topics
    public static final String KEY_MY_TOPICS = "my_topics";
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
}
