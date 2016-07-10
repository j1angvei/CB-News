package cn.j1angvei.cnbetareader.di.component;

import cn.j1angvei.cnbetareader.di.module.ArticlesModule;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.fragment.ArticlesFragment;
import dagger.Subcomponent;

/**
 * Created by Wayne on 2016/7/10.
 */
//@Component(dependencies = ActivityComponent.class, modules = ArticlesModule.class)
@PerFragment
@Subcomponent(modules = ArticlesModule.class)
public interface ArticlesComponent {
    void inject(ArticlesFragment fragment);
}
