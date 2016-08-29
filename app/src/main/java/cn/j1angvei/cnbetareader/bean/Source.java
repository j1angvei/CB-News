package cn.j1angvei.cnbetareader.bean;

/**
 * Created by Wayne on 2016/6/18.
 */

public enum Source {
    //Article
    ALL,
    POPULAR, SOFTWARE, INDUSTRY, INTERACT,
    MY_TOPICS,
    BOOKMARK,
    //Headline
    EDITORCOMMEND,
    //Review
    JHCOMMENT,
    //Topic
    ALL_TOPICS;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
