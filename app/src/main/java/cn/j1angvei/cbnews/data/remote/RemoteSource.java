package cn.j1angvei.cbnews.data.remote;

import cn.j1angvei.cbnews.data.remote.api.CBApiWrapper;
import cn.j1angvei.cbnews.util.NetworkUtil;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/23.
 */
public abstract class RemoteSource<T> {
    final CBApiWrapper mApiWrapper;
    private final NetworkUtil mNetworkUtil;

    public RemoteSource(CBApiWrapper wrapper, NetworkUtil networkUtil) {
        mApiWrapper = wrapper;
        mNetworkUtil = networkUtil;
    }

    public abstract Observable<T> fetchData(Integer page, String... args);

    boolean hasConnection() {
        return mNetworkUtil.isNetworkOn();
    }
}
