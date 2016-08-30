package cn.j1angvei.cnbetareader;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import cn.j1angvei.cnbetareader.di.component.ApplicationComponent;
import cn.j1angvei.cnbetareader.di.component.DaggerApplicationComponent;
import cn.j1angvei.cnbetareader.di.module.ApplicationModule;

/**
 * Created by Wayne on 2016/6/29.
 */
public class CBApplication extends Application {
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
