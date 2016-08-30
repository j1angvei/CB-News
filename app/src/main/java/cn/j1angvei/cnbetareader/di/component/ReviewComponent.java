package cn.j1angvei.cnbetareader.di.component;

import cn.j1angvei.cnbetareader.di.module.FragmentModule;
import cn.j1angvei.cnbetareader.di.module.sub.ReviewModule;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.fragment.ReviewFragment;
import dagger.Subcomponent;

/**
 * Created by Wayne on 2016/7/23.
 * component specific for {@link ReviewFragment}
 */
@PerFragment
@Subcomponent(modules = {ReviewModule.class, FragmentModule.class})
public interface ReviewComponent {
    void inject(ReviewFragment fragment);
}
