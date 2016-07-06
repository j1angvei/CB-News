package cn.j1angvei.cnbetareader.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wayne on 2016/6/13.
 */
public final class Headline {
    private String sid;
    private String title;
    private String content;
    private String thumb;
    private List<RelatedItem> relatedArticles;

    public Headline() {
        relatedArticles = new ArrayList<>();
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<RelatedItem> getRelatedArticles() {
        return relatedArticles;
    }

    public void setRelatedArticles(List<RelatedItem> relatedArticles) {
        this.relatedArticles = relatedArticles;
    }

}
