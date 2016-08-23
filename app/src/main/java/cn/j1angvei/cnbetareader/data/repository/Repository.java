package cn.j1angvei.cnbetareader.data.repository;

import java.util.Map;

import cn.j1angvei.cnbetareader.util.NetworkUtil;
import rx.Observable;

/**
 * Created by Wayne on 2016/7/23.
 */
public abstract class Repository<T> {
    private final NetworkUtil mNetworkUtil;
    boolean mInitLoad;

    public Repository(NetworkUtil networkUtil) {
        mNetworkUtil = networkUtil;
        mInitLoad = true;
    }

    abstract Observable<T> getData(String extra, Map<String, String> param);

    abstract void toDisk(T item);

    abstract void toRAM(T item);

    boolean connected() {
        return mNetworkUtil.isNetworkAvailable();
    }
}
