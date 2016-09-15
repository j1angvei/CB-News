package cn.j1angvei.cbnews.util;

import android.util.Log;

import cn.j1angvei.cbnews.R;
import cn.j1angvei.cbnews.exception.ItemNotFoundException;
import cn.j1angvei.cbnews.exception.JsonParseException;
import cn.j1angvei.cbnews.exception.NoMoreItemException;
import cn.j1angvei.cbnews.exception.RAMItemNotFoundException;
import cn.j1angvei.cbnews.exception.ResponseParseException;
import cn.j1angvei.cbnews.exception.SQLItemNotFoundException;
import cn.j1angvei.cbnews.exception.WEBItemNotFoundException;
import cn.j1angvei.cbnews.exception.data.LoadCacheFailException;
import cn.j1angvei.cbnews.exception.data.NoMoreDataException;
import cn.j1angvei.cbnews.exception.data.RefreshFailException;

/**
 * Created by Wayne on 2016/8/24.
 * handle exception and return information
 */

public class ErrorUtil {
    private static final String TAG = "ErrorUtil";

    public static int getErrorInfo(Throwable e) {
        Log.e(TAG, "getErrorInfo: ", e);
        if (e instanceof SQLItemNotFoundException) {
            return R.string.error_no_local_item;
        } else if (e instanceof WEBItemNotFoundException) {
            return R.string.error_no_network;
        } else if (e instanceof RAMItemNotFoundException) {
            return R.string.error_no_item_in_ram;
        } else if (e instanceof ResponseParseException) {
            return R.string.error_response_parse_fail;
        } else if (e instanceof JsonParseException) {
            return R.string.error_json_parse_fail;
        } else if (e instanceof NoMoreItemException) {
            return R.string.error_no_more_data;
        } else if (e instanceof ItemNotFoundException) {
            return R.string.error_no_data;
        } else if (e instanceof LoadCacheFailException) {

            return R.string.error_no_local_item;
        } else if (e instanceof RefreshFailException) {
            return R.string.error_no_network;

        } else if (e instanceof NoMoreDataException) {

            return R.string.error_no_more_data;
        } else {
            return R.string.error_generic;
        }
    }
}
