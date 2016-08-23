package cn.j1angvei.cnbetareader.data.local;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.data.local.helper.TopicDbHelper;
import cn.j1angvei.cnbetareader.util.PrefsUtil;
import rx.Observable;

import static android.provider.BaseColumns._ID;
import static cn.j1angvei.cnbetareader.data.local.helper.DbHelper.BLANK;
import static cn.j1angvei.cnbetareader.data.local.helper.DbHelper.LIKE;
import static cn.j1angvei.cnbetareader.data.local.helper.DbHelper.OR;
import static cn.j1angvei.cnbetareader.data.local.helper.DbHelper.QUOTE;
import static cn.j1angvei.cnbetareader.data.local.helper.DbHelper.SELECT_FROM;
import static cn.j1angvei.cnbetareader.data.local.helper.DbHelper.WHERE;

/**
 * Created by Wayne on 2016/7/23.
 */
@Singleton
public class MyTopicsLocalSource implements LocalSource<Article> {
    private static final String TAG = "MyTopicsLocalSource";
    private final TopicDbHelper mHelper;
    private final PrefsUtil mPrefsUtil;

    @Inject
    public MyTopicsLocalSource(TopicDbHelper helper, PrefsUtil prefsUtil) {
        mHelper = helper;
        mPrefsUtil = prefsUtil;
    }

    @Override
    public void create(Article item) {
    }

    @Override
    public Observable<Article> read() {
        return null;
    }

    @Override
    public void update(Article item) {

    }

    @Override
    public void delete(Article item) {

    }

    public Observable<Topic> readMyTopics() {
        Set<String> topicIds = mPrefsUtil.readStringSet(PrefsUtil.KEY_MY_TOPICS);
        if (topicIds.isEmpty()) {
            return Observable.empty();
        }
        StringBuilder builder = new StringBuilder();
        builder.append(SELECT_FROM + BLANK)
                .append(mHelper.getTableName())
                .append(BLANK + WHERE + BLANK);
        for (String id : topicIds) {
            builder.append(_ID + BLANK + LIKE + BLANK + QUOTE)
                    .append(id)
                    .append(QUOTE)
                    .append(BLANK)
                    .append(OR)
                    .append(BLANK);
        }
        String query = builder.toString();
        return mHelper.read(query.substring(0, query.length() - 4));
    }
}
