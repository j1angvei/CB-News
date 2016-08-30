package cn.j1angvei.cnbetareader.di.component;

import cn.j1angvei.cnbetareader.di.module.FragmentModule;
import cn.j1angvei.cnbetareader.di.module.sub.HeadlineModule;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.fragment.HeadlineFragment;
import dagger.Subcomponent;

/**
 * Created by Wayne on 2016/7/23.
 * component specific for {@link HeadlineFragment}
 */
@PerFragment
@Subcomponent(modules = {HeadlineModule.class, FragmentModule.class})
public interface HeadlineComponent {
    void inject(HeadlineFragment fragment);
}
