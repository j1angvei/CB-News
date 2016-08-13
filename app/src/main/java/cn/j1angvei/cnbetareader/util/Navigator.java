package cn.j1angvei.cnbetareader.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;

import cn.j1angvei.cnbetareader.activity.CommentsActivity;
import cn.j1angvei.cnbetareader.activity.ContentActivity;
import cn.j1angvei.cnbetareader.activity.PublishCommentActivity;
import cn.j1angvei.cnbetareader.activity.SettingsActivity;
import cn.j1angvei.cnbetareader.bean.Content;

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
            Intent intent = new Intent(context, ContentActivity.class);
            intent.putExtra(ContentActivity.NEWS_POSITION, position);
            intent.putStringArrayListExtra(ContentActivity.NEWS_SIDS, allSid);
            context.startActivity(intent);
        }

    }

    public static void toComments(Content content, Context context) {
        if (context != null) {
            Intent intent = new Intent(context, CommentsActivity.class);
            intent.putExtra(CommentsActivity.NEWS, content);
            context.startActivity(intent);
        }

    }

    public static void toBrowser(String sid, boolean mobileFirst, Context context) {
        if (context != null) {
            String pc = "http://www.cnbeta.com/articles/SID.htm";
            String mobile = "http://m.cnbeta.com/view/SID.htm";
            String url = mobileFirst ? mobile.replace("SID", sid) : pc.replace("SID", sid);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            context.startActivity(intent);
        }
    }

    public static void toPublishComment(boolean isAdd, String quote, String sid, String pid, Context context) {
        if (context != null) {
            Intent intent = new Intent(context, PublishCommentActivity.class);
            intent.putExtra(PublishCommentActivity.IS_ADD, isAdd);
            intent.putExtra(PublishCommentActivity.QUOTE, quote);
            intent.putExtra(PublishCommentActivity.SID, sid);
            intent.putExtra(PublishCommentActivity.PID, pid);
            context.startActivity(intent);
        }
    }
}
