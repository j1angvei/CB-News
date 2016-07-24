package cn.j1angvei.cnbetareader.di.component;

import cn.j1angvei.cnbetareader.di.module.sub.ExploreModule;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.fragment.ExploreFragment;
import dagger.Subcomponent;

/**
 * Created by Wayne on 2016/7/24.
 */
@PerFragment
@Subcomponent(modules = ExploreModule.class)
public interface ExploreComponent {
    void inject(ExploreFragment fragment);
}
