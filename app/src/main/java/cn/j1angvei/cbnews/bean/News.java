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
    private String sourceType;

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

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {

        this.sourceType = sourceType;
    }

    public static class Type {
        public static final String ARTICLE = "article";
        public static final String HEADLINE = "headline";
        public static final String REVIEW = "review";
        public static final String BOOKMARK = "bookmark";
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sid);
        dest.writeString(this.title);
        dest.writeString(this.sourceType);
    }

    public News() {
    }

    protected News(Parcel in) {
        this.sid = in.readString();
        this.title = in.readString();
        this.sourceType = in.readString();
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

        if (!sid.equals(news.sid)) return false;
        return sourceType != null ? sourceType.equals(news.sourceType) : news.sourceType == null;

    }

    @Override
    public int hashCode() {
        int result = sid.hashCode();
        result = 31 * result + (sourceType != null ? sourceType.hashCode() : 0);
        return result;
    }
}
