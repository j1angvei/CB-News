package cn.j1angvei.cbnews.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wayne on 2016/6/13.
 */
public final class Headline extends News {
    private String summary;
    private String thumb;
    private List<News> relatedNews;

    public Headline() {
        relatedNews = new ArrayList<>();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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
