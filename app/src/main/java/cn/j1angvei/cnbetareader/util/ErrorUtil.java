package cn.j1angvei.cnbetareader.util;

import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.exception.JsonParseException;
import cn.j1angvei.cnbetareader.exception.NoLocalItemException;
import cn.j1angvei.cnbetareader.exception.NoNetworkException;
import cn.j1angvei.cnbetareader.exception.ResponseParseException;

/**
 * Created by Wayne on 2016/8/24.
 */

public class ErrorUtil {
    public static int getErrorInfo(Throwable e) {
        if (e instanceof NoLocalItemException) {
            return R.string.error_no_local_item;
        } else if (e instanceof NoNetworkException) {
            return R.string.error_no_network;
        } else if (e instanceof ResponseParseException) {
            return R.string.error_response_parse_fail;
        } else if (e instanceof JsonParseException) {
            return R.string.error_json_parse_fail;
        } else {
            return R.string.error_generic;
        }
    }
}
