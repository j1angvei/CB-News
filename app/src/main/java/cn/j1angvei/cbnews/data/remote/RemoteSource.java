package cn.j1angvei.cbnews.data.remote;

import cn.j1angvei.cbnews.data.remote.api.CBApiWrapper;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/23.
 */
public abstract class RemoteSource<T> {
    final CBApiWrapper mApiWrapper;

    public RemoteSource(CBApiWrapper wrapper) {
        mApiWrapper = wrapper;
    }

    public abstract Observable<T> loadData(int page, String... args);
}
