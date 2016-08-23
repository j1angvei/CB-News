package cn.j1angvei.cnbetareader.bean;

/**
 * Created by Wayne on 2016/7/26.
 */
public class News {
    private String sid;
    private String title;

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

    public static class Type {
        public static final String ARTICLE = "article";
        public static final String HEADLINE = "headline";
        public static final String REVIEW = "review";
        public static final String BOOKMARK = "bookmark";
    }
}
