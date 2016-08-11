package cn.j1angvei.cnbetareader.bean;

/**
 * Created by Wayne on 2016/6/18.
 */

public enum Source {
    ALL, JHCOMMENT, EDITORCOMMEND, MY_TOPICS, EXPLORE, BOOKMARKS;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
