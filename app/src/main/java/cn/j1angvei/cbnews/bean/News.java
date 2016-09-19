package cn.j1angvei.cbnews.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Wayne on 2016/7/26.
 * super class of article headline review bookmark
 */
public class News implements Parcelable {
    private String sid;
    private String title;
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {

        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sid);
        dest.writeString(this.title);
        dest.writeString(this.type);
    }

    public News() {
    }

    protected News(Parcel in) {
        this.sid = in.readString();
        this.title = in.readString();
        this.type = in.readString();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel source) {
            return new News(source);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        News news = (News) o;

        if (sid != null ? !sid.equals(news.sid) : news.sid != null) return false;
        return type != null ? type.equals(news.type) : news.type == null;

    }

    @Override
    public int hashCode() {
        int result = sid != null ? sid.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

}
