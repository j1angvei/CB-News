package cn.j1angvei.cnbetareader.bean;

/**
 * Created by Wayne on 2016/6/16.
 */

public enum NewsType {
    ALL("all"),
    POPULAR("dig"),
    SOFTWARE("soft"),
    INDUSTRY("industry"),
    INTERACT("interact");

    String type;

    NewsType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
