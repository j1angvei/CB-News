package cn.j1angvei.cbnews.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Wayne on 2016/6/13.
 */
public final class Content implements Parcelable {

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
    private String title;
    private Date time;
    private String source;//where
    private String summary;//introduction
    private String detail;
    private String sid;
    private String sn;
    private String thumb;//src, topic photo

    public Content() {
    }

    private Content(Parcel in) {
        title = in.readString();
        time = new Date(in.readLong());
        source = in.readString();
        summary = in.readString();
        detail = in.readString();
        sid = in.readString();
        sn = in.readString();
        thumb = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeLong(time.getTime());
        parcel.writeString(source);
        parcel.writeString(summary);
        parcel.writeString(detail);
        parcel.writeString(sid);
        parcel.writeString(sn);
        parcel.writeString(thumb);
    }
}
