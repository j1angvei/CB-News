package cn.j1angvei.cnbetareader.data.local;

import android.text.TextUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.data.local.helper.TopicDbHelper;
import rx.Observable;

import static cn.j1angvei.cnbetareader.data.local.helper.DbHelper.BLANK;
import static cn.j1angvei.cnbetareader.data.local.helper.DbHelper.COL_LETTER;
import static cn.j1angvei.cnbetareader.data.local.helper.DbHelper.LIKE;
import static cn.j1angvei.cnbetareader.data.local.helper.DbHelper.QUOTE;
import static cn.j1angvei.cnbetareader.data.local.helper.DbHelper.SELECT_FROM;
import static cn.j1angvei.cnbetareader.data.local.helper.DbHelper.WHERE;

/**
 * Created by Wayne on 2016/7/24.
 */
@Singleton
public class ExploreLocalSource implements LocalSource<Topic> {
    private final TopicDbHelper mHelper;
    public static final String ALL_TOPICS = "all_topics";

    @Inject
    public ExploreLocalSource(TopicDbHelper helper) {
        mHelper = helper;
    }

    @Override
    public void create(Topic item) {
        mHelper.create(item);
    }

    @Override
    public Observable<Topic> read(String... args) {
        String letter = args[0];
        StringBuilder builder = new StringBuilder(SELECT_FROM + BLANK)
                .append(mHelper.getTableName());
        if (!TextUtils.equals(ALL_TOPICS, letter)) {
            builder.append(
                    BLANK + WHERE + BLANK + COL_LETTER + BLANK + LIKE + BLANK + QUOTE)
                    .append(letter)
                    .append(QUOTE);
        }
        return mHelper.read(builder.toString());
    }

    @Override
    public void update(Topic item) {

    }

    @Override
    public void delete(Topic item) {

    }
}
