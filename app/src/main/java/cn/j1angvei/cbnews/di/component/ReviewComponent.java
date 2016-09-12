package cn.j1angvei.cbnews.di.component;

import cn.j1angvei.cbnews.di.module.FragmentModule;
import cn.j1angvei.cbnews.di.module.sub.ReviewModule;
import cn.j1angvei.cbnews.di.scope.PerFragment;
import cn.j1angvei.cbnews.mytopic.ReviewFragment;
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
