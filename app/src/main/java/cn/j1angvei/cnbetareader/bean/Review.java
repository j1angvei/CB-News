package cn.j1angvei.cnbetareader.bean;

/**
 * Created by Wayne on 2016/6/13.
 */
public final class Review {
    private String articleId;
    private String commentId;
    private String title;
    private String comment;
    private String location;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    @Override
    public String toString() {
        return "Review{" +
                "articleId='" + articleId + '\'' +
                ", commentId='" + commentId + '\'' +
                ", title='" + title + '\'' +
                ", comment='" + comment + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
