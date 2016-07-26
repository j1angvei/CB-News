package cn.j1angvei.cnbetareader.converter;

import com.google.gson.Gson;

import java.util.List;

import cn.j1angvei.cnbetareader.bean.Bookmark;
import cn.j1angvei.cnbetareader.bean.News;
import cn.j1angvei.cnbetareader.util.DateUtil;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/26.
 */
public class BookmarkConverter extends NewsConverter<Bookmark> {
    public BookmarkConverter(Gson gson, String baseUrl) {
        super(gson, baseUrl);
    }

    @Override
    public Bookmark to(String json) {
        News news = mGson.fromJson(json, News.class);
        Bookmark bookmark = new Bookmark();
        bookmark.setId(news.getSid());
        bookmark.setTitle(news.getTitle());
        bookmark.setTime(DateUtil.currentDate());
        return bookmark;
    }

    @Override
    public List<Bookmark> toList(String json) {
        return null;
    }

    @Override
    public Observable<Bookmark> toObservable(String json) {
        return Observable.just(to(json));
    }
}
