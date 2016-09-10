package cn.j1angvei.cbnews.di.component;

import cn.j1angvei.cbnews.di.module.FragmentModule;
import cn.j1angvei.cbnews.di.scope.PerFragment;
import cn.j1angvei.cbnews.dialog.AddTopicDialog;
import cn.j1angvei.cbnews.dialog.PublishCmtDialog;
import cn.j1angvei.cbnews.fragment.AllTopicsFragment;
import cn.j1angvei.cbnews.fragment.ContentFragment;
import cn.j1angvei.cbnews.fragment.MyTopicsFragment;
import cn.j1angvei.cbnews.fragment.ShowCmtFragment;
import dagger.Subcomponent;

/**
 * Created by Wayne on 2016/7/25.
 */
@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(AllTopicsFragment fragment);

    void inject(ContentFragment fragment);

    void inject(ShowCmtFragment fragment);

    void inject(MyTopicsFragment fragment);

    void inject(PublishCmtDialog dialog);

    void inject(AddTopicDialog dialog);
}
