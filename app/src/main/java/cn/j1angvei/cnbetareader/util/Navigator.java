package cn.j1angvei.cnbetareader.util;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import cn.j1angvei.cnbetareader.activity.NewsContentActivity;
import cn.j1angvei.cnbetareader.activity.SettingsActivity;

/**
 * Created by Wayne on 2016/6/13.
 */
public final class Navigator {

    public static void toSettings(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, SettingsActivity.class);
            context.startActivity(intent);
        }
    }

    public static void toContent(int position, ArrayList<String> allSid, Context context) {
        if (context != null) {
            Intent intent = new Intent(context, NewsContentActivity.class);
            intent.putExtra(NewsContentActivity.NEWS_POSITION, position);
            intent.putStringArrayListExtra(NewsContentActivity.NEWS_SIDS, allSid);
            context.startActivity(intent);
        }

    }
}
