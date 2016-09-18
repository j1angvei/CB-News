package cn.j1angvei.cbnews.base;

import android.provider.BaseColumns;

/**
 * Created by Wayne on 2016/9/18.
 */

public interface AllColumns extends BaseColumns {
    /**
     * column keyword
     */
    String COL_TITLE = "title";
    String COL_THUMB = "thumb";
    String COL_LETTER = "letter";
    String COL_TOPIC = "topic";
    String COL_SUMMARY = "summary";
    String COL_COMMENT_NUM = "comment_num";
    String COL_VIEWER_NUM = "viewer_num";
    String COL_TIME = "time";
    String COL_SOURCE = "source";
    String COL_RELATED_NEWS = "related_news";
    String COL_TID = "tid";
    String COL_LOCATION = "location";
    String COL_COMMENT = "comment";
    String COL_SID = "sid";
    String COL_DETAIL = "detail";
    String COL_SN = "sn";
    String COL_TOKEN = "token";
    String COL_IS_OPEN = "is_open";
    String COL_JOIN_NUM = "join_num";
    String COL_PAGE = "page";
    String COL_HOT_TID = "hot_tid";
    String COL_ALL_TID = "all_tid";
    String COL_COMMENT_MAP = "comment_map";
    String COL_SOURCE_TYPE = "source_type";
    /**
     * Database keyword
     */
    String COMMA = ",";
    String BLANK = " ";
    String LEFT_BRACKET = "(";
    String RIGHT_BRACKET = ")";
    String PRIMARY_KEY = "PRIMARY KEY";
    String CREATE_TABLE = "CREATE TABLE";
    String DROP_TABLE = "DROP TABLE IF EXISTS";
    String SELECT_FROM = "SELECT * FROM";
    String DELETE_FROM = "DELETE FROM";
    String WHERE = "WHERE";
    String LIKE = "LIKE";
    String OR = "OR";
    String AND = "AND";
    String QUOTE = "'";
    String ORDER_BY = "ORDER BY";
    String ASCEND = "ASC";
    String DESCEND = "DESC";
    String AUTO_INCREMENT = "AUTOINCREMENT";
    String TYPE_TEXT = "TEXT";
    String TYPE_INTEGER = "INTEGER";
}
