package cn.j1angvei.cnbetareader.bean;

import java.sql.Date;

/**
 * Created by Wayne on 2016/7/24.
 */
public final class Bookmark {
    private String title;
    private String sid;
    private Date time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
