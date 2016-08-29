package cn.j1angvei.cnbetareader.bean;

/**
 * Created by Wayne on 2016/7/11.
 */
public class Topic {
    private String id;
    private String title;
    private String thumb;
    private String letter;

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

    @Override
    public String toString() {
        return "Topic{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", thumb='" + thumb + '\'' +
                ", letter='" + letter + '\'' +
                '}' + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Topic topic = (Topic) o;

        if (!id.equals(topic.id)) return false;
        if (!title.equals(topic.title)) return false;
        if (!thumb.equals(topic.thumb)) return false;
        return letter.equals(topic.letter);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + thumb.hashCode();
        result = 31 * result + letter.hashCode();
        return result;
    }
}
