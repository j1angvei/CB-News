package cn.j1angvei.cnbetareader.util;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import cn.j1angvei.cnbetareader.bean.Topic;
import rx.Observable;

/**
 * Created by Wayne on 2016/8/14.
 */

public class DbUtil {
    private static final String TAG = "DbUtil";
    //sql data type
    public static final String TYPE_INTEGER = "INTEGER";
    public static final String TYPE_TEXT = "TEXT";
    //table column name
    public static final String COL_ID = "_id";
    public static final String COL_TITLE = "title";
    public static final String COL_THUMB = "thumb";
    //column name only in article
    public static final String COL_SUMMARY = "summary";
    public static final String COL_COMMENT_NUM = "comment_num";
    public static final String COL_VIEWER_NUM = "viewer_num";
    public static final String COL_TOPIC = "topic";
    public static final String COL_TIME = "time";
    public static final String COL_SOURCE = "source";

    public static ContentValues fromTopic(Topic topic) {
        ContentValues values = new ContentValues();
        values.put(COL_ID, topic.getId());
        values.put(COL_TITLE, topic.getTitle());
        values.put(COL_THUMB, topic.getThumb());
        return values;
    }

    public static Observable<Topic> toTopic(Cursor cursor) {
        List<Topic> topics = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Topic topic = new Topic();
                topic.setId(cursor.getString(cursor.getColumnIndex(COL_ID)));
                topic.setTitle(cursor.getString(cursor.getColumnIndex(COL_TITLE)));
                topic.setThumb(cursor.getString(cursor.getColumnIndex(COL_THUMB)));
                topics.add(topic);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return Observable.from(topics);
    }
}
