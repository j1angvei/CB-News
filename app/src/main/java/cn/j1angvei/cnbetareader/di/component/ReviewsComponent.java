package cn.j1angvei.cnbetareader.di.component;

import cn.j1angvei.cnbetareader.di.module.ReviewModule;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.fragment.ReviewFragment;
import dagger.Subcomponent;

/**
 * Created by Wayne on 2016/7/10.
 */
@PerFragment
@Subcomponent(modules = ReviewModule.class)
public interface ReviewsComponent {
    void inject(ReviewFragment reviewFragment);
}
