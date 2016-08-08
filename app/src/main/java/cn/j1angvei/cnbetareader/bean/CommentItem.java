package cn.j1angvei.cnbetareader.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;


/**
 * Created by Wayne on 2016/6/13.
 */
public final class CommentItem implements Parcelable {
    @SerializedName("tid")
    private String commentId;
    @SerializedName("pid")
    private String referenceId;
    @SerializedName("sid")
    private String articleId;
    private Date date;
    @SerializedName("name")
    private String username;
    @SerializedName("icon")
    private String headPhoto;
    @SerializedName("host_name")
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.commentId);
        dest.writeString(this.referenceId);
        dest.writeString(this.articleId);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
        dest.writeString(this.username);
        dest.writeString(this.headPhoto);
        dest.writeString(this.location);
        dest.writeString(this.content);
        dest.writeString(this.upVote);
        dest.writeString(this.downVote);
    }

    public CommentItem() {
    }

    protected CommentItem(Parcel in) {
        this.commentId = in.readString();
        this.referenceId = in.readString();
        this.articleId = in.readString();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        this.username = in.readString();
        this.headPhoto = in.readString();
        this.location = in.readString();
        this.content = in.readString();
        this.upVote = in.readString();
        this.downVote = in.readString();
    }

    public static final Parcelable.Creator<CommentItem> CREATOR = new Parcelable.Creator<CommentItem>() {
        @Override
        public CommentItem createFromParcel(Parcel source) {
            return new CommentItem(source);
        }

        @Override
        public CommentItem[] newArray(int size) {
            return new CommentItem[size];
        }
    };
}