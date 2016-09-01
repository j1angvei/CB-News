package cn.j1angvei.cnbetareader.di.module;

import android.app.IntentService;

import cn.j1angvei.cnbetareader.di.scope.PerService;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Wayne on 2016/9/1.
 */
@Module
public class ServiceModule {

    private IntentService mService;

    public ServiceModule(IntentService service) {
        mService = service;
    }

    @Provides
    @PerService
    IntentService provideIntentService() {
        return mService;
    }
}
