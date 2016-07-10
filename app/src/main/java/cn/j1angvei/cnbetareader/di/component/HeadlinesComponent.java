package cn.j1angvei.cnbetareader.di.component;

import cn.j1angvei.cnbetareader.di.module.HeadlinesModule;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.fragment.HeadlineFragment;
import dagger.Subcomponent;

/**
 * Created by Wayne on 2016/7/10.
 */
@PerFragment
@Subcomponent(modules = HeadlinesModule.class)
public interface HeadlinesComponent {
    void inject(HeadlineFragment headlineFragment);
}
