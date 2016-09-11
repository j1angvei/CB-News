package cn.j1angvei.cbnews.di.component;

import cn.j1angvei.cbnews.di.module.FragmentModule;
import cn.j1angvei.cbnews.di.scope.PerFragment;
import cn.j1angvei.cbnews.dialog.AddTopicDialog;
import cn.j1angvei.cbnews.dialog.PublishCmtDialog;
import cn.j1angvei.cbnews.fragment.CommentFragment;
import cn.j1angvei.cbnews.fragment.TopicFragment;
import cn.j1angvei.cbnews.fragment.ContentFragment;
import cn.j1angvei.cbnews.fragment.MyTopicFragment;
import dagger.Subcomponent;

/**
 * Created by Wayne on 2016/7/25.
 */
@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(TopicFragment fragment);

    void inject(ContentFragment fragment);

    void inject(CommentFragment fragment);

    void inject(MyTopicFragment fragment);

    void inject(PublishCmtDialog dialog);

    void inject(AddTopicDialog dialog);
}
