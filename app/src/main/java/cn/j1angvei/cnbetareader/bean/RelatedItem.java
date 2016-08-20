package cn.j1angvei.cnbetareader.bean;

/**
 * Created by Wayne on 2016/6/13.
 */
public final class RelatedItem {
    private String sid;
    private String title;

    public RelatedItem(String sid, String title) {
        this.sid = sid;
        this.title = title;
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

}