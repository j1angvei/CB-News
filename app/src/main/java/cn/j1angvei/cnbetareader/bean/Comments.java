package cn.j1angvei.cnbetareader.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wayne on 2016/6/13.
 */
public final class Comments {
    private String token;
    private boolean open;
    private String sid;
    private String joinNum;
    private String commentNum;
    private String page;

    private List<String> hotIds;
    private List<String> allIds;
    private Map<String, CommentItem> commentMap;

    public Comments() {
        hotIds = new ArrayList<>();
        allIds = new ArrayList<>();
        commentMap = new HashMap<>();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getJoinNum() {
        return joinNum;
    }

    public void setJoinNum(String joinNum) {
        this.joinNum = joinNum;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<String> getHotIds() {
        return hotIds;
    }

    public void setHotIds(List<String> hotIds) {
        this.hotIds = hotIds;
    }

    public List<String> getAllIds() {
        return allIds;
    }

    public void setAllIds(List<String> allIds) {
        this.allIds = allIds;
    }

    public Map<String, CommentItem> getCommentMap() {
        return commentMap;
    }

    public void setCommentMap(Map<String, CommentItem> commentMap) {
        this.commentMap = commentMap;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "token='" + token + '\'' +
                ", open=" + open +
                ", sid='" + sid + '\'' +
                ", joinNum='" + joinNum + '\'' +
                ", commentNum='" + commentNum + '\'' +
                ", page='" + page + '\'' +
                ", hotIds=" + hotIds +
                ", allIds=" + allIds +
                ", commentMap=" + commentMap +
                '}';
    }
}

