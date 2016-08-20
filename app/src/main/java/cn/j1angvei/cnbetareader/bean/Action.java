package cn.j1angvei.cnbetareader.bean;

/**
 * Created by Wayne on 2016/8/9.
 * Action of operating comment
 */
public enum Action {
    SUPPORT, AGAINST, REPORT, REPLY, PUBLISH;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
