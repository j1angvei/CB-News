package cn.j1angvei.cnbetareader.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Wayne on 2016/6/13.
 */
public final class CommentItem {
    @SerializedName("tid")
    private String commentId;
    @SerializedName("pid")
    private String referenceId;
    @SerializedName("sid")
    private String articleId;
    private String date;
    @SerializedName("name")
    private String username;
    @SerializedName("icon")
    private String headPhoto;
    @SerializedName("hostName")
    private String location;
    @SerializedName("comment")
    private String content;
    @SerializedName("score")
    private String upVote;
    @SerializedName("reason")
    private String downVote;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeadPhoto() {
        return headPhoto;
    }

    public void setHeadPhoto(String headPhoto) {
        this.headPhoto = headPhoto;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpVote() {
        return upVote;
    }

    public void setUpVote(String upVote) {
        this.upVote = upVote;
    }

    public String getDownVote() {
        return downVote;
    }

    public void setDownVote(String downVote) {
        this.downVote = downVote;
    }

    @Override
    public String toString() {
        return "CommentItem{" +
                "commentId='" + commentId + '\'' +
                ", referenceId='" + referenceId + '\'' +
                ", articleId='" + articleId + '\'' +
                ", date='" + date + '\'' +
                ", username='" + username + '\'' +
                ", headPhoto='" + headPhoto + '\'' +
                ", location='" + location + '\'' +
                ", content='" + content + '\'' +
                ", upVote='" + upVote + '\'' +
                ", downVote='" + downVote + '\'' +
                '}';
    }
}