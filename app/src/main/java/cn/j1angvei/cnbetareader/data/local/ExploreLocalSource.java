package cn.j1angvei.cnbetareader.data.local;

import android.app.Application;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Topic;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/24.
 */
@Singleton
public class ExploreLocalSource extends LocalSource<Topic> {
    @Inject
    public ExploreLocalSource(Application context) {
        super(context);
    }

    @Override
    Observable<Topic> get() {
        return null;
    }

    @Override
    void save(Topic item) {

    }
}
