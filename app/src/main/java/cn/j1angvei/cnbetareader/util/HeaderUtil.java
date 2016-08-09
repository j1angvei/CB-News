package cn.j1angvei.cnbetareader.util;

/**
 * Created by Wayne on 2016/8/9.
 */
public class HeaderUtil {
    private static final String PH_SID = "sid";
    public static final String VALUE_ACCPET_IMG = "image/png, image/svg+xml, image/jxr, image/*;q=0.8, */*;q=0.5";
    private static final String VALUE_REFERER = "http://www.cnbeta.com/articles/" + PH_SID + ".htm";

    public static String getReferer(String sid) {
        return VALUE_REFERER.replace(PH_SID, sid);
    }
}
