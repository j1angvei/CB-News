package cn.j1angvei.cbnews.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;

import cn.j1angvei.cbnews.CBApplication;
import cn.j1angvei.cbnews.di.component.ApplicationComponent;
import cn.j1angvei.cbnews.di.component.ServiceComponent;

/**
 * Created by Wayne on 2016/9/1.
 */

public abstract class BaseService extends IntentService {
    protected ServiceComponent mServiceComponent;

    public BaseService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        doInjection();
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((CBApplication) getApplication()).getApplicationComponent();
    }

    public ServiceComponent getServiceComponent() {
        return mServiceComponent;
    }

    protected NotificationManager getNotificationMgr() {
        return (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    protected abstract void doInjection();
}
