package cn.j1angvei.cnbetareader.data.local;

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
        String query = SELECT_FROM + BLANK + mHelper.getTableName() + BLANK + WHERE + BLANK + COL_LETTER + BLANK + LIKE + BLANK + QUOTE + letter + QUOTE;
        return mHelper.read(query);
    }

    @Override
    public void update(Topic item) {

    }

    @Override
    public void delete(Topic item) {

    }
}
