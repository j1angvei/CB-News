package cn.j1angvei.cnbetareader.data.local.helper;

import android.provider.BaseColumns;

import rx.Observable;

/**
 * Created by Wayne on 2016/8/20.
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
    //column type
    String TYPE_TEXT = "TEXT";
    //table names
    String TABLE_TOPIC = "topic";
    String TABLE_ARTICLE = "article";
    String TABLE_HEADLINE = "headline";
    String TABLE_REVIEW = "review";
    String TABLE_CONTENT = "content";
    String TABLE_COMMENTS = "comments";
    String TABLE_COMMENT_ITEM = "comment_item";
    //column name
    String COL_TITLE = "title";
    String COL_THUMB = "thumb";
    String COL_LETTER = "letter";
    String COL_SUMMARY = "summary";
    String COL_COMMENT_NUM = "comment_num";
    String COL_VIEWER_NUM = "viewer_num";
    String COL_TOPIC = "topic";
    String COL_TIME = "time";
    String COL_SOURCE = "source";


    void create(T item);

    Observable<T> read(String query);

    void update(T item);

    void delete(T item);

}
