package cn.j1angvei.cnbetareader.bean;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Wayne on 2016/7/24.
 */
public final class Bookmark {
    private String title;
    @SerializedName("sid")
    private String id;
    private Date time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
