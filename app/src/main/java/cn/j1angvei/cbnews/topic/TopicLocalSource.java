package cn.j1angvei.cbnews.topic;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.base.DbHelper;
import cn.j1angvei.cbnews.base.LocalSource;
import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.di.qualifier.QTopic;
import rx.Observable;

import static android.provider.BaseColumns._ID;
import static cn.j1angvei.cbnews.base.DbHelper.BLANK;
import static cn.j1angvei.cbnews.base.DbHelper.COL_LETTER;
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
    public Observable<Topic> read(@NonNull String param) {
        String column = TextUtils.isDigitsOnly(param) ? _ID : COL_LETTER;
        String query = SELECT_FROM + BLANK + mDbHelper.getTableName() + BLANK +
                WHERE + BLANK + column + BLANK + LIKE + BLANK + QUOTE + param + QUOTE;
        return mDbHelper.read(query);
    }

}
