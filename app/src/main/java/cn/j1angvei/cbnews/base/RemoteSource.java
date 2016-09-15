package cn.j1angvei.cbnews.base;

import cn.j1angvei.cbnews.exception.NoNetworkException;
import cn.j1angvei.cbnews.util.NetworkUtil;
import cn.j1angvei.cbnews.web.CBApiWrapper;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/23.
 */
public abstract class RemoteSource<T> {
    protected final CBApiWrapper mApiWrapper;
    protected final Converter<T> mConverter;
    private final NetworkUtil mNetworkUtil;

    public RemoteSource(CBApiWrapper wrapper, Converter<T> converter, NetworkUtil networkUtil) {
        mApiWrapper = wrapper;
        mConverter = converter;
        mNetworkUtil = networkUtil;
    }


    public Observable<T> fetch(int page, String id, String extra) {
        return Observable.error(new NoNetworkException());//default
    }

    protected boolean hasConnection() {
        return mNetworkUtil.isNetworkOn();
    }
}
