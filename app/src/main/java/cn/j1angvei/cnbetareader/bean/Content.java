package cn.j1angvei.cnbetareader.bean;

import java.util.Date;

/**
 * Created by Wayne on 2016/6/13.
 */
public final class Content {

    private String title;
    private Date date;

    private String source;//where
    private String summary;//introduction
    private String detail;
    private String sid;
    private String sn;
    private String token;
    private String topicPhoto;//src

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTopicPhoto() {
        return topicPhoto;
    }

    public void setTopicPhoto(String topicPhoto) {
        this.topicPhoto = topicPhoto;
    }

    @Override
    public String toString() {
        return "Content{" +
                "\ntitle='" + title + '\'' +
                ",\n date='" + date + '\'' +
                ",\n source='" + source + '\'' +
                ",\n summary='" + summary + '\'' +
                ", \ndetail='" + detail + '\'' +
                ", \nsid='" + sid + '\'' +
                ", \nsn='" + sn + '\'' +
                ", \ntoken='" + token + '\'' +
                ", \ntopicPhoto='" + topicPhoto + '\'' +
                '}';
    }
}
