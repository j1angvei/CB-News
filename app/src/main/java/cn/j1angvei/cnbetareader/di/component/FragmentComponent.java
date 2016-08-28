package cn.j1angvei.cnbetareader.di.component;

import cn.j1angvei.cnbetareader.di.module.FragmentModule;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.dialog.AddTopicDialog;
import cn.j1angvei.cnbetareader.dialog.PublishCmtDialog;
import cn.j1angvei.cnbetareader.fragment.ContentFragment;
import cn.j1angvei.cnbetareader.fragment.MyTopicsFragment;
import cn.j1angvei.cnbetareader.fragment.ShowCmtFragment;
import cn.j1angvei.cnbetareader.fragment.TopicFragment;
import dagger.Subcomponent;

/**
 * Created by Wayne on 2016/7/25.
 */
@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(TopicFragment fragment);

    void inject(ContentFragment fragment);

    void inject(ShowCmtFragment fragment);

    void inject(MyTopicsFragment fragment);

    void inject(PublishCmtDialog dialog);

    void inject(AddTopicDialog dialog);
}
