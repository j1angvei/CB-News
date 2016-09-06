package cn.j1angvei.cbnews.data.local;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.data.local.helper.TopicDbHelper;
import cn.j1angvei.cbnews.util.ApiUtil;
import rx.Observable;

import static cn.j1angvei.cbnews.data.local.helper.DbHelper.BLANK;
import static cn.j1angvei.cbnews.data.local.helper.DbHelper.COL_PAGE;
import static cn.j1angvei.cbnews.data.local.helper.DbHelper.LIKE;
import static cn.j1angvei.cbnews.data.local.helper.DbHelper.ORDER_BY;
import static cn.j1angvei.cbnews.data.local.helper.DbHelper.QUOTE;
import static cn.j1angvei.cbnews.data.local.helper.DbHelper.SELECT_FROM;
import static cn.j1angvei.cbnews.data.local.helper.DbHelper.WHERE;

/**
 * Created by Wayne on 2016/7/24.
 */
@Singleton
public class AllTopicsLocalSource implements LocalSource<Topic> {
    private final TopicDbHelper mHelper;
    public static final String ALL_TOPICS = "all_topics";

    @Inject
    public AllTopicsLocalSource(TopicDbHelper helper) {
        mHelper = helper;
    }

    @Override
    public void create(Topic item) {
        mHelper.create(item);
    }

    @Override
    public Observable<Topic> read(@NonNull Integer page, String id, String sourceType) {//id and sourceType is null
        String letter = ApiUtil.pageToLetter(page);
        StringBuilder builder = new StringBuilder(SELECT_FROM + BLANK)
                .append(mHelper.getTableName());
        if (!TextUtils.equals(ALL_TOPICS, letter)) {
            builder.append(
                    BLANK + WHERE + BLANK + COL_PAGE + BLANK + LIKE + BLANK + QUOTE)
                    .append(letter)
                    .append(QUOTE);
        }
        builder.append(BLANK + ORDER_BY + BLANK + COL_PAGE);
        return mHelper.read(builder.toString());
    }

    @Override
    public void update(Topic item) {

    }

    @Override
    public void delete(Topic item) {

    }
}
