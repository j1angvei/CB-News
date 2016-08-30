package cn.j1angvei.cnbetareader.bean;

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
    private String letter;
    private boolean isAdded;

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

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {

        this.letter = letter;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", thumb='" + thumb + '\'' +
                ", letter='" + letter + '\'' +
                ", isAdded=" + isAdded +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Topic topic = (Topic) o;

        if (isAdded != topic.isAdded) return false;
        if (id != null ? !id.equals(topic.id) : topic.id != null) return false;
        if (title != null ? !title.equals(topic.title) : topic.title != null) return false;
        if (thumb != null ? !thumb.equals(topic.thumb) : topic.thumb != null) return false;
        return letter != null ? letter.equals(topic.letter) : topic.letter == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (thumb != null ? thumb.hashCode() : 0);
        result = 31 * result + (letter != null ? letter.hashCode() : 0);
        result = 31 * result + (isAdded ? 1 : 0);
        return result;
    }

    public Topic() {
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
        dest.writeString(this.letter);
        dest.writeByte(this.isAdded ? (byte) 1 : (byte) 0);
    }

    protected Topic(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.thumb = in.readString();
        this.letter = in.readString();
        this.isAdded = in.readByte() != 0;
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
}
