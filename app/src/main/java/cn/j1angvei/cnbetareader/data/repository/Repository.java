package cn.j1angvei.cnbetareader.data.repository;

import java.util.Map;

import cn.j1angvei.cnbetareader.util.NetworkUtil;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/23.
 */
public abstract class Repository<T> {
    private final NetworkUtil mNetworkUtil;

    public Repository(NetworkUtil networkUtil) {
        mNetworkUtil = networkUtil;
    }

    abstract Observable<T> getData(String extra, Map<String, String> param);

    public abstract void toDisk(T item);

    abstract void toRAM(T item);

    boolean isConnected() {
        return mNetworkUtil.isNetworkOn();
    }
}
