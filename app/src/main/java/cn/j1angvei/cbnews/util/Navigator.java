package cn.j1angvei.cbnews.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;

import cn.j1angvei.cbnews.newscomments.CommentActivity;
import cn.j1angvei.cbnews.newscontent.ContentActivity;
import cn.j1angvei.cbnews.imageviewer.ImageActivity;
import cn.j1angvei.cbnews.newslist.NewsActivity;
import cn.j1angvei.cbnews.settings.SettingsActivity;
import cn.j1angvei.cbnews.topicnews.TopicNewsActivity;
import cn.j1angvei.cbnews.bean.News;
import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.service.OfflineDownloadService;

/**
 * Created by Wayne on 2016/6/13.
 * navigate between components
 */
public final class Navigator {

    public static void toSettings(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, SettingsActivity.class);
            context.startActivity(intent);
        }
    }

    public static void toNewsList(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, NewsActivity.class);
            context.startActivity(intent);
        }
    }

    public static void toContent(int position, ArrayList<? extends News> newses, Context context) {
        if (context != null) {
            Intent intent = new Intent(context, ContentActivity.class);
            intent.putExtra(ContentActivity.NEWS_POSITION, position);
            intent.putParcelableArrayListExtra(ContentActivity.NEWS_LIST, newses);
            context.startActivity(intent);
        }
    }

    public static void toComments(String sid, String title, Context context) {
        if (context != null) {
            Intent intent = new Intent(context, CommentActivity.class);
            intent.putExtra(CommentActivity.NEWS_ID, sid);
            intent.putExtra(CommentActivity.NEWS_TITLE, title);
            context.startActivity(intent);
        }
    }

    public static void toTopicNews(Topic topic, Context context) {
        if (context != null) {
            Intent intent = new Intent(context, TopicNewsActivity.class);
            intent.putExtra(TopicNewsActivity.TOPIC, topic);
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

    public static void toExit(boolean isExit, Context context) {
        if (context != null) {
            Intent intent = new Intent(context, NewsActivity.class);
            intent.putExtra(NewsActivity.EXIT, isExit);
            context.startActivity(intent);
        }
    }

    public static void toContentImage(int pos, String[] urls, Context context) {
        if (context != null) {
            Intent intent = new Intent(context, ImageActivity.class);
            intent.putExtra(ImageActivity.CUR_POS, pos);
            intent.putExtra(ImageActivity.IMG_URLS, urls);
            context.startActivity(intent);
        }
    }

    public static void toOfflineDownload(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, OfflineDownloadService.class);
            context.startService(intent);
        }

    }
}
