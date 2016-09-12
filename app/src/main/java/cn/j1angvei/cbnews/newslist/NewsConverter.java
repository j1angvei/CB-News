package cn.j1angvei.cbnews.newslist;

import com.google.gson.Gson;

import cn.j1angvei.cbnews.base.Converter;
import cn.j1angvei.cbnews.bean.News;

/**
 * Created by Wayne on 2016/7/23.
 */
public abstract class NewsConverter<T extends News> implements Converter<T> {
    protected final Gson mGson;

    public NewsConverter(Gson gson) {
        mGson = gson;
    }
}
