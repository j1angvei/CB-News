package cn.j1angvei.cnbetareader.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Wayne on 2016/6/18.
 */
public final class DateUtil {
    private static final String TAG = "DateUtil";

    public static String toShortDatePlusTime(Date date, Context context) {
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
        return String.format("%s %s", dateFormat.format(date), toTime(date, context));
    }

    public static String toLongDatePlusTime(Date date, Context context) {
        DateFormat dateFormat = android.text.format.DateFormat.getLongDateFormat(context);
        return String.format("%s %s", dateFormat.format(date), toTime(date, context));
    }

    public static String toTime(Date date, Context context) {
        return android.text.format.DateFormat.getTimeFormat(context).format(date);
    }

    public static Date toDate(String dateString, DateFormatType type) throws ParseException {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat(type.getType());
        return sdf.parse(dateString);
    }

    public enum DateFormatType {
        CNBETA("yyyy-MM-dd HH:mm:ss"),
        SHORT("yyyy-MM-dd");
        private String type;

        DateFormatType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
