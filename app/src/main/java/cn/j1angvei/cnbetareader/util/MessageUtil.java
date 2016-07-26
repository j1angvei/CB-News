package cn.j1angvei.cnbetareader.util;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Wayne on 2016/6/13.
 */
public final class MessageUtil {

    public static void longToast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void shortToast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void longSnack(View view) {
        Snackbar.make(view, "snack bar", Snackbar.LENGTH_LONG);
    }


}
