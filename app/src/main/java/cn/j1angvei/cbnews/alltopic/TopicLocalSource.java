package cn.j1angvei.cbnews.alltopic;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.base.LocalSource;
import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.base.DbHelper;
import cn.j1angvei.cbnews.di.qualifier.QTopic;
import rx.Observable;

import static android.provider.BaseColumns._ID;
import static cn.j1angvei.cbnews.base.DbHelper.BLANK;
import static cn.j1angvei.cbnews.base.DbHelper.COL_PAGE;
import static cn.j1angvei.cbnews.base.DbHelper.LIKE;
import static cn.j1angvei.cbnews.base.DbHelper.QUOTE;
import static cn.j1angvei.cbnews.base.DbHelper.SELECT_FROM;
import static cn.j1angvei.cbnews.base.DbHelper.WHERE;

/**
 * Created by Wayne on 2016/7/24.
 */
@Singleton
public class TopicLocalSource extends LocalSource<Topic> {
    private static final String TAG = "TopicLocalSource";

    @Inject
    public TopicLocalSource(@QTopic DbHelper<Topic> dbHelper) {
        super(dbHelper);
    }

    @Override
    public Observable<Topic> read(int page, String id, String extra) {
        StringBuilder builder = new StringBuilder(SELECT_FROM + BLANK).append(mDbHelper.getTableName());
        if (id != null) {
            builder.append(BLANK + WHERE + BLANK + _ID + BLANK + LIKE + BLANK + QUOTE)
                    .append(id)
                    .append(QUOTE);
        } else {
            builder.append(BLANK + WHERE + BLANK + COL_PAGE + BLANK + LIKE + BLANK + QUOTE)
                    .append(page)
                    .append(QUOTE);
        }
        return mDbHelper.read(builder.toString());
    }

    @Override
    public void update(Topic item) {

    }

    @Override
    public void delete(Topic item) {

    }
}
