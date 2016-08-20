package cn.j1angvei.cnbetareader.converter;

import android.text.Html;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Headline;
import cn.j1angvei.cnbetareader.bean.News;
import cn.j1angvei.cnbetareader.bean.RawHeadline;
import cn.j1angvei.cnbetareader.data.remote.response.ExposedResponse;
import cn.j1angvei.cnbetareader.util.ApiUtil;
import cn.j1angvei.cnbetareader.util.StringUtil;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/22.
 */
@Singleton
public class HeadlineConverter extends NewsConverter<Headline> {
    @Inject
    public HeadlineConverter(Gson gson) {
        super(gson);
    }

    @Override
    public Headline to(String json) {
        return null;
    }

    @Override
    public List<Headline> toList(String jsonp) {
        String json = ApiUtil.removeJsonpWrapper(jsonp);
        ExposedResponse<RawHeadline> response = mGson.fromJson(json, new TypeToken<ExposedResponse<RawHeadline>>() {
        }.getType());
        List<RawHeadline> rawHeadlines = response.getResult();
        List<Headline> headlines = new ArrayList<>();
        for (RawHeadline raw : rawHeadlines) {
            headlines.add(convert(raw));
        }
        return headlines;
    }

    @Override
    public Observable<Headline> toObservable(String json) {
        return Observable.from(toList(json));
    }

    private Headline convert(RawHeadline raw) {
        Headline headline = new Headline();
        headline.setSid(raw.getFromId());
        headline.setTitle(Html.fromHtml(raw.getTitle()).toString());
        String content = Html.fromHtml(raw.getDescription()).toString();
        headline.setContent(StringUtil.removeBlanks(content));

        ArrayList<News> newses = new ArrayList<>();
        for (String relation : raw.getRelatedArticles()) {
            String sid = relation.replaceFirst("^<a href=.*/articles/", "").replaceAll("\\.htm\\\" target=\\\"_blank\\\">.*</a>$", "");
            String title = Html.fromHtml(relation).toString();
            News news = new News();
            news.setSid(sid);
            news.setTitle(title);
            newses.add(news);
        }
        headline.setRelatedNews(newses);
        headline.setThumb(raw.getThumb());
        return headline;
    }
}
