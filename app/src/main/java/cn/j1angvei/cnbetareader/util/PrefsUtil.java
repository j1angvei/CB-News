package cn.j1angvei.cnbetareader.util;

import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Wayne on 2016/8/10.
 * read and write sharedPreferences
 */
@Singleton
public class PrefsUtil {
    private static final String CSRF_TOKEN = "csrf_token";
    SharedPreferences prefs;

    @Inject
    public PrefsUtil(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    public String readToken() {
        return prefs.getString(CSRF_TOKEN, "");
    }

    public void writeToken(String token) {
        prefs.edit().putString(CSRF_TOKEN, token).apply();
    }
}
