package cn.j1angvei.cnbetareader.bean;

/**
 * Created by Wayne on 2016/6/18.
 */

public enum Source {

    LATEST_NEWS("all"),
    HOT_COMMENT("jhcomment"),
    PAST_HEADLINE("editorcommend"),
    MY_TOPICS("my_topics"),
    ALL_TOPICS("explore"),
    BOOKMARKS("bookmarks");

    private String value;

    Source(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
