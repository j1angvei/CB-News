package cn.j1angvei.cnbetareader;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

import cn.j1angvei.cnbetareader.di.component.ApplicationComponent;
import cn.j1angvei.cnbetareader.di.component.DaggerApplicationComponent;
import cn.j1angvei.cnbetareader.di.module.ApplicationModule;
import cn.j1angvei.cnbetareader.fragment.SettingsFragment;
import cn.j1angvei.cnbetareader.util.PrefsUtil;

/**
 * Created by Wayne on 2016/6/29.
 */
public class CBApplication extends Application {
    //    static {
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//    }
    @Inject
    PrefsUtil mPrefsUtil;

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        mApplicationComponent.inject(this);
        boolean isNight = mPrefsUtil.readBoolean(SettingsFragment.PREF_NIGHT_MODE);
        AppCompatDelegate.setDefaultNightMode(isNight ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
