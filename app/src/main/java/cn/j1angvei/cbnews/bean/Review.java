package cn.j1angvei.cbnews.bean;

/**
 * Created by Wayne on 2016/6/13.
 */
public final class Review extends News {
    private String tid;
    private String comment;
    private String location;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
