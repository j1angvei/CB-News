package cn.j1angvei.cnbetareader.data.repository;

import cn.j1angvei.cnbetareader.util.NetworkUtil;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/23.
 * implement repository pattern
 */
public abstract class Repository<T> {
    private final NetworkUtil mNetworkUtil;

    public Repository(NetworkUtil networkUtil) {
        mNetworkUtil = networkUtil;
    }

    public abstract Observable<T> getData(int page, String id, String typeOrSN);

    public abstract void storeToDisk(T item);

    abstract void storeToMemory(T item);

    boolean isConnected() {
        return mNetworkUtil.isNetworkOn();
    }
}
