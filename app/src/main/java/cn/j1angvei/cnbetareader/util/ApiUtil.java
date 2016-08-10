package cn.j1angvei.cnbetareader.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Wayne on 2016/8/10.
 */
public class ApiUtil {
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
}
