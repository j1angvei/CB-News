package cn.j1angvei.cnbetareader.di.component;

import cn.j1angvei.cnbetareader.di.module.sub.NestedTopicsModule;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.fragment.NestedTopicsFragment;
import dagger.Subcomponent;

/**
 * Created by Wayne on 2016/7/23.
 */
@PerFragment
@Subcomponent(modules = NestedTopicsModule.class)
public interface NestedTopicsComponent {
    void inject(NestedTopicsFragment fragment);
}
