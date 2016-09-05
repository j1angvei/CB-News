package cn.j1angvei.cbnews.data.local.helper;

import android.provider.BaseColumns;

import rx.Observable;

/**
 * Created by Wayne on 2016/8/20.
 * Store common column names, comment methods in {@link android.database.sqlite.SQLiteOpenHelper}
 */

public interface DbHelper<T> extends BaseColumns {
    //keyword
    String COMMA = ",";
    String BLANK = " ";
    String LEFT_BRACKET = "(";
    String RIGHT_BRACKET = ")";
    String PRIMARY_KEY = "PRIMARY KEY";
    String CREATE_TABLE = "CREATE TABLE";
    String DROP_TABLE = "DROP TABLE IF EXISTS";
    String SELECT_FROM = "SELECT * FROM";
    String WHERE = "WHERE";
    String LIKE = "LIKE";
    String OR = "OR";
    String AND = "AND";
    String QUOTE = "'";
    String ORDER_BY = "ORDER BY";
    String ASCEND = "ASC";
    String DESCEND = "DESC";
    String AUTO_INCREMENT = "AUTOINCREMENT";
    //column type
    String TYPE_TEXT = "TEXT";
    String TYPE_INTEGER = "INTEGER";
    //column name
    //from topic
    String COL_TITLE = "title";
    String COL_THUMB = "thumb";
    //from article
    String COL_TOPIC = "topic";
    String COL_SUMMARY = "summary";
    String COL_COMMENT_NUM = "comment_num";
    String COL_VIEWER_NUM = "viewer_num";
    String COL_TIME = "time";
    String COL_SOURCE = "source";
    //from headline
    String COL_RELATED_NEWS = "related_news";
    //from review
    String COL_TID = "tid";
    String COL_LOCATION = "location";
    String COL_COMMENT = "comment";
    //from content
    String COL_SID = "sid";
    String COL_DETAIL = "detail";
    String COL_SN = "sn";
    //from comments
    String COL_TOKEN = "token";
    String COL_IS_OPEN = "is_open";
    String COL_JOIN_NUM = "join_num";
    String COL_PAGE = "page";
    String COL_HOT_TID = "hot_tid";
    String COL_ALL_TID = "all_tid";
    String COL_COMMENT_MAP = "comment_map";
    //use to judge the sourceType
    String COL_SOURCE_TYPE = "source_type";
    //use to reorder row
    String COL_ADD_ORDER = "add_order";


    void create(T item);

    Observable<T> read(String query);

    void update(T item);

    void delete(T item);

    String getTableName();

}
