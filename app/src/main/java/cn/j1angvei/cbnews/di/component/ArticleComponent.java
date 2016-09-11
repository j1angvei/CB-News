package cn.j1angvei.cbnews.di.component;

import cn.j1angvei.cbnews.di.module.FragmentModule;
import cn.j1angvei.cbnews.di.module.sub.ArticleModule;
import cn.j1angvei.cbnews.di.scope.PerFragment;
import cn.j1angvei.cbnews.fragment.ArticleFragment;
import dagger.Subcomponent;


/**
 * Created by Wayne on 2016/7/22.
 * component specific for {@link ArticleFragment}
 */
@PerFragment
@Subcomponent(modules = {ArticleModule.class, FragmentModule.class})
public interface ArticleComponent {
    void inject(ArticleFragment fragment);
}
