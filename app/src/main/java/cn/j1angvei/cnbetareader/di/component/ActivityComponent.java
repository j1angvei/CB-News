package cn.j1angvei.cnbetareader.di.component;

import cn.j1angvei.cnbetareader.di.module.ActivityModule;
import cn.j1angvei.cnbetareader.di.module.FragmentModule;
import cn.j1angvei.cnbetareader.di.scope.PerActivity;
import dagger.Component;

/**
 * Created by Wayne on 2016/6/15.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    FragmentComponent fragmentComponent(FragmentModule fragmentModule);
}
