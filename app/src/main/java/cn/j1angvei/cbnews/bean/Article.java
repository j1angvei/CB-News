package cn.j1angvei.cbnews.bean;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Wayne on 2016/6/13.
 */
public final class Article extends News implements Parcelable {
    private String topic;
    @SerializedName("hometext")
    private String summary;
    @SerializedName("comments")
    private String commentNum;
    @SerializedName("counter")
    private String viewerNum;
    @SerializedName("inputtime")
    private Date time;
    private String thumb;
    private String source;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public String getViewerNum() {
        return viewerNum;
    }

    public void setViewerNum(String viewerNum) {
        this.viewerNum = viewerNum;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


}

