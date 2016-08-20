package cn.j1angvei.cnbetareader.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wayne on 2016/6/13.
 */
public final class Headline extends News {
    private String content;
    private String thumb;
    private List<News> relatedNews;

    public Headline() {
        relatedNews = new ArrayList<>();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public List<News> getRelatedNews() {
        return relatedNews;
    }

    public void setRelatedNews(List<News> relatedNews) {
        this.relatedNews = relatedNews;
    }
}
