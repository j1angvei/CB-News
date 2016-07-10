package cn.j1angvei.cnbetareader.di.component;

import cn.j1angvei.cnbetareader.di.module.TopicsModule;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.fragment.NestedTopicsFragment;
import dagger.Subcomponent;

/**
 * Created by Wayne on 2016/7/10.
 */
@PerFragment
@Subcomponent(modules = TopicsModule.class)
public interface TopicsComponent {
    void inject(NestedTopicsFragment fragment);
}
