package cn.j1angvei.cnbetareader.util;

/**
 * Created by Wayne on 2016/7/6.
 */
public class StringUtil {
    public static String removeAllBlanks(String input) {
        return input.replaceAll("\\s+", "");
    }
}