package cn.j1angvei.cnbetareader.bean;

/**
 * Created by Wayne on 2016/6/18.
 */

public enum SourceType {
    ALL("all"),
    POPULAR("dig"),
    SOFTWARE("soft"),
    INDUSTRY("industry"),
    INTERACT("interact"),
    HEADLINE("headline"),
    HOT_COMMENT("hot_comment");

    private String type;

    SourceType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
