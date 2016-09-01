package cn.j1angvei.cnbetareader.converter;

import com.google.gson.Gson;

import cn.j1angvei.cnbetareader.bean.News;

/**
 * Created by Wayne on 2016/7/23.
 */
public abstract class NewsConverter<T extends News> implements Converter<String, T> {
    final Gson mGson;

    public NewsConverter(Gson gson) {
        mGson = gson;
    }
}
