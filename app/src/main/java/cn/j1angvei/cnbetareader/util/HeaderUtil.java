package cn.j1angvei.cnbetareader.util;

/**
 * Created by Wayne on 2016/8/9.
 */
public class HeaderUtil {
    /**
     * These Headers will be added using {@link cn.j1angvei.cnbetareader.data.remote.interceptor.AddHeaderInterceptor}
     */
    public static final String KEY_CONNECTION = "Connection";
    public static final String VALUE_CONNECTION = "keep-alive";

    public static final String KEY_LANGUAGE = "Accept-Language";
    public static final String VALUE_LANGUAGE = "zh-CN,zh;q=0.8";

    public static final String KEY_HOST = "Host";
    public static final String VALUE_HOST = "www.cnbeta.com";

    public static final String KEY_UA = " User-Agent";
    public static final String VALUE_UA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36";

    /**
     * Cookie will automatically handled by {@link okhttp3.CookieJar}
     */
    public static final String KEY_COOKIE = "Cookie";

    /**
     * If no header in request, this will add to the request in {@link cn.j1angvei.cnbetareader.data.remote.interceptor.AddHeaderInterceptor}
     */
    public static final String KEY_REFERER = "Referer";
    public static final String VALUE_REFER = "http://www.cnbeta.com/";

    public static final String KEY_ACCEPT = "Accept";
    public static final String VALUE_ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8";

    /**
     * Specific Headers, which are added in {@link cn.j1angvei.cnbetareader.data.remote.CnbetaApi}
     */
    private static final String KEY_AJAX = "X-Requested-With";
    private static final String VALUE_AJAX = "XMLHttpRequest";
    private static final String VALUE_ACCEPT_JSON = "application/json, text/javascript, */*; q=0.01";
    private static final String VALUE_ACCEPT_IMG = "image/webp,image/*,*/*;q=0.8";
    private static final String KEY_CACHE = "Cache-Control";
    private static final String VALUE_CACHE_MAX = "max-age=0";
    private static final String VALUE_CACHE_NO = "no-cache";
    private static final String KEY_PRAGMA = "Pragma";
    private static final String VALUE_PRAGMA = "no-cache";

    //response is json format
    public static final String HEADER_ACCEPT_JSON = KEY_ACCEPT + ":" + VALUE_ACCEPT_JSON;
    //response is image format
    public static final String HEADER_ACCEPT_IMG = KEY_ACCEPT + ":" + VALUE_ACCEPT_IMG;
    //request is ajax format
    public static final String HEADER_AJAX = KEY_AJAX + ":" + VALUE_AJAX;
    //maximum cache,such as in all topics
    public static final String HEADER_CACHE_MAX = KEY_CACHE + ":" + VALUE_CACHE_MAX;
    public static final String HEADER_CACHE_NO = KEY_CACHE + ":" + VALUE_CACHE_NO;
    public static final String HEADER_PRAGMA = KEY_PRAGMA + ":" + VALUE_PRAGMA;
    /**
     * Headers must be handled by okhttp
     */
    private static final String KEY_ENCODING = "Accept-Encoding";
    private static final String VALUE_ENCODING = "gzip, deflate";

    /**
     * Dynamic Headers
     */
    //need specific string to form the header
    private static final String PH_SID = "sid";
    private static final String VALUE_REFERER_CONTENT = "http://www.cnbeta.com/articles/" + PH_SID + ".htm";

    public static String getRefererValue(String sid) {
        return VALUE_REFERER_CONTENT.replace(PH_SID, sid);
    }
}
