package cn.j1angvei.cnbetareader.util;

/**
 * Created by Wayne on 2016/6/13.
 */

public final class JsonpGenerator {
    private static final String JQUERY = "jQuery";
    private static final String JQUERY_VERSION = "1.8.0";
    private static String PARAMETER;
    private static long INIT_TIME;

    static {
        INIT_TIME = System.currentTimeMillis();
        String randomString = JQUERY_VERSION + Math.random();
        PARAMETER = String.format("%s%s_%s", JQUERY, randomString.replaceAll("\\D", ""), INIT_TIME);
    }

    public static String getParameter() {
        return PARAMETER;
    }

    public static long getInitTime() {
        return INIT_TIME;
    }
}
