package cn.j1angvei.cnbetareader.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Wayne on 2016/6/13.
 */
public final class ToastUtil {

    public static void showLongToast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showShortToast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
