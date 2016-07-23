package cn.j1angvei.cnbetareader.converter;

import com.google.gson.Gson;

/**
 * Created by Wayne on 2016/7/23.
 */
public abstract class NewsConverter<T> implements Converter<T> {
    final Gson mGson;
    final String mBaseUrl;

    public NewsConverter(Gson gson, String baseUrl) {
        mGson = gson;
        mBaseUrl = baseUrl;
    }
}
