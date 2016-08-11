package cn.j1angvei.cnbetareader.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Wayne on 2016/6/13.
 */
public final class Content implements Parcelable {

    private String title;
    private Date date;
    private String source;//where
    private String summary;//introduction
    private String detail;
    private String sid;
    private String sn;
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

    public String getTopicPhoto() {
        return topicPhoto;
    }

    public void setTopicPhoto(String topicPhoto) {
        this.topicPhoto = topicPhoto;
    }

    public Content() {
    }

    private Content(Parcel in) {
        title = in.readString();
        date = new Date(in.readLong());
        source = in.readString();
        summary = in.readString();
        detail = in.readString();
        sid = in.readString();
        sn = in.readString();
        topicPhoto = in.readString();
    }

    public static final Creator<Content> CREATOR = new Creator<Content>() {
        @Override
        public Content createFromParcel(Parcel parcel) {
            return new Content(parcel);
        }

        @Override
        public Content[] newArray(int i) {
            return new Content[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeLong(date.getTime());
        parcel.writeString(source);
        parcel.writeString(summary);
        parcel.writeString(detail);
        parcel.writeString(summary);
        parcel.writeString(sn);
        parcel.writeString(topicPhoto);
    }
}
