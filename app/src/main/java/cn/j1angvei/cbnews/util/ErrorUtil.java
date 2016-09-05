package cn.j1angvei.cbnews.util;

import android.util.Log;

import cn.j1angvei.cbnews.R;
import cn.j1angvei.cbnews.exception.JsonParseException;
import cn.j1angvei.cbnews.exception.NoLocalItemException;
import cn.j1angvei.cbnews.exception.NoNetworkException;
import cn.j1angvei.cbnews.exception.ResponseParseException;

/**
 * Created by Wayne on 2016/8/24.
 * handle exception and return information
 */

public class ErrorUtil {
    private static final String TAG = "ErrorUtil";

    public static int getErrorInfo(Throwable e) {
        Log.e(TAG, "getErrorInfo: ", e);
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
