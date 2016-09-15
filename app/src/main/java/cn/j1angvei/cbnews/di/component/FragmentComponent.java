package cn.j1angvei.cbnews.di.component;

import cn.j1angvei.cbnews.di.module.FragmentModule;
import cn.j1angvei.cbnews.di.scope.PerFragment;
import cn.j1angvei.cbnews.addtopic.AddTopicDialog;
import cn.j1angvei.cbnews.addcomment.PublishCmtDialog;
import cn.j1angvei.cbnews.newscomments.CommentFragment;
import cn.j1angvei.cbnews.topic.TopicFragment;
import cn.j1angvei.cbnews.newscontent.ContentFragment;
import cn.j1angvei.cbnews.newslist.mytopic.MyTopicFragment;
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
