package cn.j1angvei.cnbetareader.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Wayne on 2016/8/10.
 */
public class ApiUtil {
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

    public static Map<String, String> getNewsParam(String jsoncallback, String type, int page, long timestamp) {
        Map<String, String> map = new HashMap<>();
        map.put(KEY_CALLBACK, jsoncallback);
        map.put(KEY_TYPE, type);
        map.put(KEY_PAGE, String.valueOf(page));
        map.put(KEY_TIMESTAMP, String.valueOf(timestamp));
        return map;
    }

    public static Map<String, String> getTopicsNewsParam(String jsoncallback, String id, int page, long timestamp) {
        Map<String, String> map = new HashMap<>();
        map.put(KEY_CALLBACK, jsoncallback);
        map.put(KEY_ID, id);
        map.put(KEY_PAGE, String.valueOf(page));
        map.put(KEY_TIMESTAMP, String.valueOf(timestamp));
        return map;
    }

    public static Map<String, String> getCommentsParam(String token, String sid, String sn) {
        Map<String, String> map = new HashMap<>();
        map.put(KEY_CSRF_TOKEN, token);
        String op = getCommentsOp(sid, sn);
        map.put(KEY_OP, op);
        return map;
    }

    private static String getCommentsOp(String sid, String sn) {
        return String.format("1,%s,%s", sid, sn);
    }

    public static Map<String, String> getOperateCommentParam(String token, String action, String sid, String tid) {
        Map<String, String> map = new HashMap<>();
        map.put(KEY_CSRF_TOKEN, token);
        map.put(KEY_OP, action);
        map.put(KEY_SID, sid);
        map.put(KEY_TID, tid);
        return map;
    }

    public static Map<String, String> getPublishCommentParam(String token, String action, String content, String captcha, String sid, String pid) {
        Map<String, String> map = new HashMap<>();
        map.put(KEY_CSRF_TOKEN, token);
        map.put(KEY_OP, action);
        map.put(KEY_CONTENT, content);
        map.put(KEY_CAPTCHA, captcha);
        map.put(KEY_SID, sid);
        map.put(KEY_PID, pid);
        return map;
    }

    public static Map<String, String> getCaptchaUrlParam(String token, long timestamp, String refresh) {
        Map<String, String> map = new HashMap<>();
        map.put(KEY_CSRF_TOKEN, token);
        map.put(KEY_TIMESTAMP, String.valueOf(timestamp));
        //refresh are always "1"
        map.put(KEY_REFRESH, refresh);
        return map;
    }

    public static String parseCaptchaParamV(String json) {
        return json.substring(json.lastIndexOf('='), json.lastIndexOf('"'));
    }
}