package cn.j1angvei.cnbetareader.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wayne on 2016/6/13.
 */
public final class RawHeadline {
    private String fromId;
    private String title;
    private String description;
    private String thumb;
    @SerializedName("relation")
    private List<String> relatedArticles;

    public RawHeadline() {
        relatedArticles = new ArrayList<>();
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public List<String> getRelatedArticles() {
        return relatedArticles;
    }

    public void setRelatedArticles(List<String> relatedArticles) {
        this.relatedArticles = relatedArticles;
    }
}
