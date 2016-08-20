package cn.j1angvei.cnbetareader.util;

import android.annotation.SuppressLint;
import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Wayne on 2016/6/18.
 */
public final class DateUtil {
    public static final String DATE_FORMAT_CB = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_MEDIUM = "yyyy-MM-dd";
    public static final String DATE_FORMAT_SHORT = "MM-dd";

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

    public static Date toDate(String dateString, String format) throws ParseException {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(dateString);
    }

    public static String convertDefault(Date date) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_CB);
        return sdf.format(date);
    }

    public static Date parseDefault(String source) throws ParseException {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_CB);
        return sdf.parse(source);
    }
}
