package cn.j1angvei.cnbetareader.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.News;

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
}
