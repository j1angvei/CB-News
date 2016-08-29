package cn.j1angvei.cnbetareader.di.component;

import cn.j1angvei.cnbetareader.di.module.FragmentModule;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.dialog.AddMyTopicDialog;
import cn.j1angvei.cnbetareader.dialog.PublishCmtDialog;
import cn.j1angvei.cnbetareader.fragment.AllTopicsFragment;
import cn.j1angvei.cnbetareader.fragment.ContentFragment;
import cn.j1angvei.cnbetareader.fragment.MyTopicsFragment;
import cn.j1angvei.cnbetareader.fragment.ShowCmtFragment;
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

    void inject(AddMyTopicDialog dialog);
}
