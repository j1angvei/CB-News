package cn.j1angvei.cbnews.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.bean.CommentItem;
import cn.j1angvei.cbnews.bean.News;

/**
 * Created by Wayne on 2016/8/20.
 */
@Singleton
public class DbUtil {
    private final Gson mGson;

    @Inject
    public DbUtil(Gson gson) {
        mGson = gson;
    }

    public String convertNewsList(List<News> newsList) {
        return mGson.toJson(newsList);
    }

    public List<News> parseNewsList(String newsString) {
        Type type = new TypeToken<List<News>>() {
        }.getType();
        return mGson.fromJson(newsString, type);
    }

    public String convertStringList(List<String> stringList) {
        return mGson.toJson(stringList);
    }

    public List<String> parseStringList(String str) {
        Type type = new TypeToken<List<String>>() {
        }.getType();
        return mGson.fromJson(str, type);
    }

    public String convertCommentMap(Map<String, CommentItem> map) {
        return mGson.toJson(map);
    }

    public Map<String, CommentItem> parseCommentMap(String mapString) {
        Type type = new TypeToken<Map<String, CommentItem>>() {
        }.getType();
        return mGson.fromJson(mapString, type);
    }
}
