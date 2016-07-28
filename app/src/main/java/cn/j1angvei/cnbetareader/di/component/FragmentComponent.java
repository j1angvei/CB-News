package cn.j1angvei.cnbetareader.di.component;

import cn.j1angvei.cnbetareader.di.module.FragmentModule;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.fragment.CommentsFragment;
import cn.j1angvei.cnbetareader.fragment.ContentFragment;
import cn.j1angvei.cnbetareader.fragment.ExploreFragment;
import cn.j1angvei.cnbetareader.fragment.NestedTopicsFragment;
import dagger.Subcomponent;

/**
 * Created by Wayne on 2016/7/25.
 */
@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(ExploreFragment fragment);

    void inject(NestedTopicsFragment fragment);

    void inject(ContentFragment fragment);

    void inject(CommentsFragment fragment);
}
