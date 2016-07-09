package cn.j1angvei.cnbetareader.di.component;

import cn.j1angvei.cnbetareader.di.module.FragmentModule;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.fragment.ReviewFragment;
import cn.j1angvei.cnbetareader.fragment.ArticlesFragment;
import cn.j1angvei.cnbetareader.fragment.HeadlineFragment;
import dagger.Subcomponent;

/**
 * Created by Wayne on 2016/6/16.
 */
@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(ArticlesFragment articlesFragment);

    void inject(ReviewFragment reviewFragment);

    void inject(HeadlineFragment headlineFragment);

}
