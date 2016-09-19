package cn.j1angvei.cbnews.util;

import android.util.Log;

import cn.j1angvei.cbnews.R;
import cn.j1angvei.cbnews.exception.ItemNotFoundException;
import cn.j1angvei.cbnews.exception.NoCacheException;

/**
 * Created by Wayne on 2016/8/24.
 * handle exception and return information
 */

public class ErrorUtil {
    private static final String TAG = "ErrorUtil";

    public static int getErrorInfo(Throwable e) {
        Log.e(TAG, "getErrorInfo: ", e);
        if (e instanceof ItemNotFoundException) {
            return R.string.error_no_data;
        } else if (e instanceof NoCacheException) {

            return R.string.error_no_cache_found;
        } else {
            return R.string.error_generic;
        }
    }
}
