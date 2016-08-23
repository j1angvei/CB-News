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
    private final Application mApplication;

    @Inject
    public NetworkUtil(Application application) {
        mApplication = application;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mApplication.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
