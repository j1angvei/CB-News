package cn.j1angvei.cnbetareader.bean;

/**
 * Created by Wayne on 2016/6/13.
 */
public final class Review extends News {
    private String commentId;
    private String comment;
    private String location;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
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
