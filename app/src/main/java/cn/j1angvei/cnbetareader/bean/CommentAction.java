package cn.j1angvei.cnbetareader.bean;

/**
 * Created by Wayne on 2016/8/9.
 */
public enum CommentAction {
    SUPPORT, AGAINST, REPORT, REPLY, PUBLISH;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
