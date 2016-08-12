package cn.j1angvei.cnbetareader.data.remote.api;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.util.PrefsUtil;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by Wayne on 2016/8/12.
 * handle cookies from request and response，as only 2 cookies matters, csrf_token and PHPSESSID
 */
@Singleton
public class CnBetaCookieJar implements CookieJar {
    private static final String TAG = "CnBetaCookieJar";
    private final List<Cookie> COOKIE_STORE;
    private static final String NAME_TOKEN = "csrf_token";
    private static final String NAME_SESSION = "PHPSESSID";
    private PrefsUtil mPrefsUtil;

    @Inject
    public CnBetaCookieJar(PrefsUtil prefsUtil) {
        mPrefsUtil = prefsUtil;
        COOKIE_STORE = initCookieStore(PrefsUtil.COOKIE_TOKEN, PrefsUtil.COOKIE_SESSION);
    }

    @Override
    public void saveFromResponse(HttpUrl url, final List<Cookie> cookies) {
        //save to memory
        COOKIE_STORE.addAll(cookies);
        //sve to preferences
        new Thread(new Runnable() {
            @Override
            public void run() {
                saveCookie(PrefsUtil.COOKIE_TOKEN, NAME_TOKEN, cookies);
                saveCookie(PrefsUtil.COOKIE_SESSION, NAME_SESSION, cookies);
            }
        }).run();
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return COOKIE_STORE;
    }

    private List<Cookie> initCookieStore(String... keys) {
        List<Cookie> cookies = new ArrayList<>();
        for (String key : keys) {
            String cookieString = mPrefsUtil.readString(key);
            if (TextUtils.isEmpty(cookieString)) {
                break;
            }
            String[] cookieArray = cookieString.split(" ");
            Cookie cookie = new Cookie.Builder()
                    .name(cookieArray[0])
                    .value(cookieArray[1])
                    .domain(cookieArray[2])
                    .path(cookieArray[3])
                    .build();
            cookies.add(cookie);
        }
        return cookies;
    }

    private void saveCookie(String key, String name, List<Cookie> cookies) {
        Cookie cookie = null;
        for (Cookie c : cookies) {
            if (TextUtils.equals(name, c.name())) {
                cookie = c;
                break;
            }
        }
        if (cookie != null) {
            String cookieString = String.format("%s %s %s %s",
                    cookie.name(),
                    cookie.value(),
                    cookie.domain(),
                    cookie.path());
            mPrefsUtil.writeString(key, cookieString);
        } else {
            Log.d(TAG, "saveCookie: null cookie failed to save");
        }
    }
}
