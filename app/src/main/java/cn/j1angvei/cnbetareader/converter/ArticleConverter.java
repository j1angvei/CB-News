package cn.j1angvei.cnbetareader.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.data.remote.response.WrappedResponse;
import cn.j1angvei.cnbetareader.util.ApiUtil;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/22.
 */
@Singleton
public class ArticleConverter extends NewsConverter<Article> {

    @Inject
    public ArticleConverter(Gson gson) {
        super(gson);
    }

    @Override
    public Article to(String json) {
        return null;
    }

    @Override
    public List<Article> toList(String jsonp) {
        String json = ApiUtil.removeJsonpWrapper(jsonp);
        WrappedResponse<Article> response = mGson.fromJson(json, new TypeToken<WrappedResponse<Article>>() {
        }.getType());
        return response.getResult().getList();
    }

    @Override
    public Observable<Article> toObservable(String json) {
        return Observable.from(toList(json));
    }
}
