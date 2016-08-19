package cn.j1angvei.cnbetareader.util;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Wayne on 2016/6/13.
 * handle display toast and snack message
 */
public final class MessageUtil {

    public static void toast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void toast(int infoId, Context context) {
        Toast.makeText(context, infoId, Toast.LENGTH_SHORT).show();
    }

    public static void snack(View view, int stringId) {
        Snackbar.make(view, stringId, Snackbar.LENGTH_LONG).show();
    }

    public static void snack(View view, CharSequence info) {
        Snackbar.make(view, info, Snackbar.LENGTH_LONG).show();
    }

    public static void snackWithAction(View view, int infoId, int actionId, View.OnClickListener listener) {
        Snackbar.make(view, infoId, Snackbar.LENGTH_INDEFINITE)
                .setAction(actionId, listener)
                .show();

    }
}
