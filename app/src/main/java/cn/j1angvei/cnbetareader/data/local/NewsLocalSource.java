package cn.j1angvei.cnbetareader.data.local;

import android.content.Context;

import cn.j1angvei.cnbetareader.data.local.LocalSource;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/23.
 */
public class NewsLocalSource<T> extends LocalSource<T> {

    public NewsLocalSource(Context context) {
        super(context);
    }

    @Override
    Observable<T> get() {
        return null;
    }

    @Override
    public void save(T item) {

    }

}
