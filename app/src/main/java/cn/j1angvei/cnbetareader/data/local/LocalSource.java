package cn.j1angvei.cnbetareader.data.local;

import android.app.Application;
import android.content.Context;

import rx.Observable;

/**
 * Created by Wayne on 2016/7/23.
 */
public abstract class LocalSource<T> {
    final Context mContext;

    public LocalSource(Application context) {
        mContext = context;
    }

    abstract Observable<T> get();

    abstract void save(T item);
}
