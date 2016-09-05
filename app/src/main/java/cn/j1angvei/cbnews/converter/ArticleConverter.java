package cn.j1angvei.cbnews.converter;

import android.text.Html;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.bean.Article;
import cn.j1angvei.cbnews.data.remote.response.WrappedResponse;
import cn.j1angvei.cbnews.util.ApiUtil;
import rx.Observable;
import rx.functions.Action1;

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
        List<Article> articles = toList(json);
        if (articles == null) {
            return Observable.empty();
        }
        return Observable.from(articles)
                .doOnNext(new Action1<Article>() {
                    @Override
                    public void call(Article article) {
                        article.setSource(ApiUtil.removeAtChar(article.getSource()));
                        String summary = Html.fromHtml(article.getSummary()).toString();
                        article.setSummary(summary);
                    }
                });
    }
}
