package cn.j1angvei.cbnews.util;

import cn.j1angvei.cbnews.web.AddHeaderInterceptor;
import cn.j1angvei.cbnews.web.CnbetaApi;

/**
 * Created by Wayne on 2016/8/9.
 * manage all Headers in HTTP request
 */
public class HeaderUtil {
    /**
     * These Headers will be added using {@link AddHeaderInterceptor}
     */
    public static final String KEY_CONNECTION = "Connection";
    public static final String VALUE_CONNECTION = "keep-alive";

    public static final String KEY_LANGUAGE = "Accept-Language";
    public static final String VALUE_LANGUAGE = "zh-CN,zh;q=0.8";

    public static final String KEY_HOST = "Host";
    public static final String VALUE_HOST = "www.cnbeta.com";

    public static final String KEY_UA = "User-Agent";
    public static final String VALUE_UA =
            "Mozilla/5.0 (Linux; Android 6.0.1; Nexus 6 Build/MOB30O) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.98 Mobile Safari/537.36";

    /**
     * If no header in request, this will add to the request in {@link AddHeaderInterceptor}
     */
    public static final String KEY_REFERER = "Referer";
    public static final String VALUE_REFER = "http://www.cnbeta.com/";

    public static final String KEY_ACCEPT = "Accept";
    public static final String VALUE_ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8";

    /**
     * Specific Headers, which are added in {@link CnbetaApi}
     */
    private static final String KEY_AJAX = "X-Requested-With";
    private static final String VALUE_AJAX = "XMLHttpRequest";
    //request is ajax format
    public static final String HEADER_AJAX = KEY_AJAX + ":" + VALUE_AJAX;
    private static final String VALUE_ACCEPT_JSON = "application/json, text/javascript, */*; q=0.01";
    //response is json format
    public static final String HEADER_ACCEPT_JSON = KEY_ACCEPT + ":" + VALUE_ACCEPT_JSON;
    private static final String VALUE_ACCEPT_IMG = "image/webp,image/*,*/*;q=0.8";
    //response is image format
    public static final String HEADER_ACCEPT_IMG = KEY_ACCEPT + ":" + VALUE_ACCEPT_IMG;
    private static final String KEY_CACHE = "Cache-Control";
    private static final String VALUE_CACHE_MAX = "max-age=0";
    //maximum cachedData,such as in all topics
    public static final String HEADER_CACHE_MAX = KEY_CACHE + ":" + VALUE_CACHE_MAX;
    private static final String VALUE_CACHE_NO = "no-cachedData";
    public static final String HEADER_CACHE_NO = KEY_CACHE + ":" + VALUE_CACHE_NO;
    private static final String KEY_PRAGMA = "Pragma";
    private static final String VALUE_PRAGMA = "no-cachedData";
    public static final String HEADER_PRAGMA = KEY_PRAGMA + ":" + VALUE_PRAGMA;

    /**
     * Dynamic Headers
     */
    //need specific string to form the header
    private static final String VALUE_REFERER_HEAD = "http://www.cnbeta.com/articles/";
    private static final String VALUE_REFERER_TAIL = ".htm";

    public static String assembleRefererValue(String sid) {
        return VALUE_REFERER_HEAD + sid + VALUE_REFERER_TAIL;
    }
}
