package cn.j1angvei.cnbetareader.bean;

/**
 * Created by Wayne on 2016/6/13.
 */
public final class RawReview {
    private String fromId;
    private String title;
    private String description;

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "RawReview{" +
                "fromId='" + fromId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
