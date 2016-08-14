package cn.j1angvei.cnbetareader.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Wayne on 2016/8/10.
 * api relevant operation, parse String, assemble parameter Map
 */
@Singleton
public final class ApiUtil {
    public static final String BASE_URL = "http://www.cnbeta.com";
    //parameter in request
    private static final String KEY_CALLBACK = "jsoncallback";
    private static final String KEY_TYPE = "type";
    private static final String KEY_PAGE = "page";
    private static final String KEY_TIMESTAMP = "_";
    private static final String KEY_ID = "id";
    private static final String KEY_CSRF_TOKEN = "csrf_token";
    private static final String KEY_OP = "op";
    public static final String KEY_SID = "sid";
    private static final String KEY_TID = "tid";
    private static final String KEY_PID = "pid";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_CAPTCHA = "seccode";
    private static final String KEY_REFRESH = "refresh";
    public static final String KEY_V = "v";
    public static final String KEY_LETTER = "letter";

    private final PrefsUtil mPrefsUtil;

    @Inject
    public ApiUtil(PrefsUtil prefsUtil) {
        mPrefsUtil = prefsUtil;
    }

    public static String parseToken(String html) {
        Pattern pattern = Pattern.compile("TOKEN:\".+?\"");
        Matcher matcher = pattern.matcher(html);
        String token = "";
        if (matcher.find()) {
            token = matcher.group();
            token = token.substring(token.indexOf('"') + 1, token.lastIndexOf('"'));
        }
        return token;
    }

    public static String pageToLetter(int page) {
        return "" + (char) ('a' + page - 1);
    }

    public static String removeJsonpWrapper(String jsonp) {
        return jsonp.substring(jsonp.indexOf('{'), jsonp.lastIndexOf('}') + 1);
    }

    public Map<String, String> getNewsParam(String type, int page) {
        Map<String, String> map = new HashMap<>();
        map.put(KEY_CALLBACK, JsonpUtil.getParameter());
        map.put(KEY_TYPE, type);
        map.put(KEY_PAGE, String.valueOf(page));
        map.put(KEY_CSRF_TOKEN, readToken());
        map.put(KEY_TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        return map;
    }

    public Map<String, String> getTopicsNewsParam(String topicId, int page) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put(KEY_CALLBACK, JsonpUtil.getParameter());
        map.put(KEY_ID, topicId);
        map.put(KEY_PAGE, String.valueOf(page));
        map.put(KEY_CSRF_TOKEN, readToken());
        map.put(KEY_TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        return map;
    }

    public Map<String, String> getCommentsParam(String sid, String sn) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put(KEY_CSRF_TOKEN, readToken());
        String op = assembleCommentsOp(sid, sn);
        map.put(KEY_OP, op);
        return map;
    }

    private static String assembleCommentsOp(String sid, String sn) {
        return String.format("1,%s,%s", sid, sn);
    }

    public Map<String, String> getJudgeCommentParam(String action, String sid, String tid) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put(KEY_CSRF_TOKEN, readToken());
        map.put(KEY_OP, action);
        map.put(KEY_SID, sid);
        map.put(KEY_TID, tid);
        return map;
    }

    public Map<String, String> getPublishCommentParam(String content, String captcha, String sid, String pid) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put(KEY_CSRF_TOKEN, readToken());
        //action always to be publish
        String action = "publish";
        map.put(KEY_OP, action);
        map.put(KEY_CONTENT, content);
        map.put(KEY_CAPTCHA, captcha);
        map.put(KEY_SID, sid);
        map.put(KEY_PID, pid);
        return map;
    }

    public Map<String, String> getCaptchaUrlParam() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put(KEY_CSRF_TOKEN, readToken());
        map.put(KEY_TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        //refresh are always "1"
        String refresh = "1";
        map.put(KEY_REFRESH, refresh);
        return map;
    }

    public static String parseCaptchaParamV(String json) {
        return json.substring(json.lastIndexOf('='), json.lastIndexOf('"'));
    }

    private String readToken() {
        return mPrefsUtil.readString(PrefsUtil.CSRF_TOKEN);
    }
}
