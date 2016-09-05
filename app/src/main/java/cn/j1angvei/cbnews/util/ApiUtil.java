package cn.j1angvei.cbnews.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Wayne on 2016/8/10.
 * api relevant operation, parse String, assemble parameter Map
 */
public final class ApiUtil {
    public static final String BASE_URL = "http://www.cnbeta.com";

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

    public static String assembleCommentsOp(String sid, String sn) {
        return String.format("1,%s,%s", sid, sn);
    }

    public static String parseCaptchaVerify(String json) {
        return json.substring(json.lastIndexOf('='), json.lastIndexOf('"'));
    }

    public static String getJsonp() {
        final String jQuery = "jQuery";
        final String jqueryVersion = "1.8.0";
        String randomString = jqueryVersion + Math.random();
        return String.format("%s%s_%s", jQuery, randomString.replaceAll("\\D", ""), System.currentTimeMillis());
    }

    public static String removeAtChar(String source) {
        if (source == null) {
            return "null";
        } else if (!source.contains("@")) {
            return source;
        } else if (source.startsWith("@")) {
            return source.substring(1);
        } else {
            return source.substring(0, source.lastIndexOf("@"));
        }

    }
}
