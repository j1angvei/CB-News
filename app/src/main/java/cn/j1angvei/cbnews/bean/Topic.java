package cn.j1angvei.cbnews.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Wayne on 2016/7/11.
 * bean represent a Topic in CB
 */
public class Topic implements Parcelable {
    private String id;
    private String title;
    private String thumb;
    private boolean isAdded;
    private int page;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.thumb);
        dest.writeByte(this.isAdded ? (byte) 1 : (byte) 0);
        dest.writeInt(this.page);
    }

    public Topic() {
    }

    protected Topic(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.thumb = in.readString();
        this.isAdded = in.readByte() != 0;
        this.page = in.readInt();
    }

    public static final Creator<Topic> CREATOR = new Creator<Topic>() {
        @Override
        public Topic createFromParcel(Parcel source) {
            return new Topic(source);
        }

        @Override
        public Topic[] newArray(int size) {
            return new Topic[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Topic topic = (Topic) o;

        if (isAdded != topic.isAdded) return false;
        if (page != topic.page) return false;
        if (id != null ? !id.equals(topic.id) : topic.id != null) return false;
        if (title != null ? !title.equals(topic.title) : topic.title != null) return false;
        return thumb != null ? thumb.equals(topic.thumb) : topic.thumb == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (thumb != null ? thumb.hashCode() : 0);
        result = 31 * result + (isAdded ? 1 : 0);
        result = 31 * result + page;
        return result;
    }
}