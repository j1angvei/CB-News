package cn.j1angvei.cnbetareader.util;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Wayne on 2016/8/24.
 */
@Singleton
public class NetworkUtil {
    private final ConnectivityManager mConnMgr;

    @Inject
    public NetworkUtil(Application application) {
        mConnMgr = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public boolean isNetworkOn() {
        NetworkInfo activeNetworkInfo = mConnMgr.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public boolean isWifiOn() {
        return isNetworkOn() && mConnMgr.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }

    public boolean isCellularOn() {
        NetworkInfo activeNetworkInfo = mConnMgr.getActiveNetworkInfo();
        return isNetworkOn() && mConnMgr.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_MOBILE;
    }
}
