package cn.j1angvei.cnbetareader.converter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.util.List;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.data.remote.response.WrappedResponse;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/22.
 */
public class ArticleConverter extends NewsConverter<Article> {

    public ArticleConverter(Gson gson, String baseUrl) {
        super(gson, baseUrl);
    }

    @Override
    public Article to(String json) {
        return null;
    }

    @Override
    public List<Article> toList(String json) {
        WrappedResponse<Article> response = mGson.fromJson(json, new TypeToken<WrappedResponse<Article>>() {
        }.getType());
        return response.getResult().getList();
    }

    @Override
    public Observable<Article> toObservable(String json) {
        return Observable.from(toList(json));
    }
}
