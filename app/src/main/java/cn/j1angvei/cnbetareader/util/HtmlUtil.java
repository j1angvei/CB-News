package cn.j1angvei.cnbetareader.util;

/**
 * Created by Wayne on 2016/9/2.
 * operate news content in html format
 */

public class HtmlUtil {
    private static final String ASSETS_DIR = "file:///android_asset/";
    private static final String CSS = ASSETS_DIR + "style.css";
    private static final String JS = ASSETS_DIR + "content.js";

    public static String wrapContent(String content) {
        return "<!DOCTYPE html><html><head>" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "<meta name=\"viewport\" content=\"width=device-width, user-scalable=no\" >" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"" + CSS + "\"/>" +
                "</head><body id=\"main\">" + content + "<script src=\"" + JS + "\"></script>" + "</body></html>";
    }
}
