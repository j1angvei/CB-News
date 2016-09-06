package cn.j1angvei.cbnews;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

import cn.j1angvei.cbnews.di.component.ApplicationComponent;
import cn.j1angvei.cbnews.di.component.DaggerApplicationComponent;
import cn.j1angvei.cbnews.di.module.ApplicationModule;
import cn.j1angvei.cbnews.util.AppUtil;

/**
 * Created by Wayne on 2016/6/29.
 */
public class CBApplication extends Application {
    @Inject
    AppUtil mAppUtil;
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        mApplicationComponent.inject(this);
        mAppUtil.initAppTheme();
        mAppUtil.initLocaleAsChina();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
