package cn.j1angvei.cbnews.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;


/**
 * Created by Wayne on 2016/6/13.
 * represent one comment item
 */
public final class CommentItem implements Parcelable {
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
    private String tid;
    private String pid;
    private String sid;
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
    private String support;
    @SerializedName("reason")
    private String against;

    public CommentItem() {
    }

    protected CommentItem(Parcel in) {
        this.tid = in.readString();
        this.pid = in.readString();
        this.sid = in.readString();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        this.username = in.readString();
        this.headPhoto = in.readString();
        this.location = in.readString();
        this.content = in.readString();
        this.support = in.readString();
        this.against = in.readString();
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
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

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public String getAgainst() {
        return against;
    }

    public void setAgainst(String against) {
        this.against = against;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tid);
        dest.writeString(this.pid);
        dest.writeString(this.sid);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
        dest.writeString(this.username);
        dest.writeString(this.headPhoto);
        dest.writeString(this.location);
        dest.writeString(this.content);
        dest.writeString(this.support);
        dest.writeString(this.against);
    }
}