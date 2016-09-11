package cn.j1angvei.cbnews.data.remote;

import cn.j1angvei.cbnews.converter.Converter;
import cn.j1angvei.cbnews.data.remote.api.CBApiWrapper;
import cn.j1angvei.cbnews.util.NetworkUtil;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/23.
 */
public abstract class RemoteSource<T> {
    final CBApiWrapper mApiWrapper;
    final Converter<T> mConverter;
    private final NetworkUtil mNetworkUtil;

    public RemoteSource(CBApiWrapper wrapper, Converter<T> converter, NetworkUtil networkUtil) {
        mApiWrapper = wrapper;
        mConverter = converter;
        mNetworkUtil = networkUtil;
    }


    public abstract Observable<T> fetchData(int page, String id, String extra);

    boolean hasConnection() {
        return mNetworkUtil.isNetworkOn();
    }
}
