package cn.j1angvei.cnbetareader.di.component;

import cn.j1angvei.cnbetareader.di.module.sub.ArticleModule;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.fragment.ArticlesFragment;
import dagger.Subcomponent;


/**
 * Created by Wayne on 2016/7/22.
 */
@PerFragment
@Subcomponent(modules = ArticleModule.class)
public interface ArticleComponent {
    void inject(ArticlesFragment fragment);
}
