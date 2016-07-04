package cn.j1angvei.cnbetareader.di.component;

import android.app.Application;

import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.data.DataRepository;
import cn.j1angvei.cnbetareader.di.module.ApplicationModule;
import dagger.Component;

/**
 * Created by Wayne on 2016/6/15.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    //expose to sub activity graph
    Application application();

    DataRepository dataRepository();
}
