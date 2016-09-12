package cn.j1angvei.cbnews.di.component;

import cn.j1angvei.cbnews.newscomments.CommentActivity;
import cn.j1angvei.cbnews.newscontent.ContentActivity;
import cn.j1angvei.cbnews.newslist.NewsActivity;
import cn.j1angvei.cbnews.topicnews.TopicNewsActivity;
import cn.j1angvei.cbnews.di.module.ActivityModule;
import cn.j1angvei.cbnews.di.module.FragmentModule;
import cn.j1angvei.cbnews.di.module.sub.ArticleModule;
import cn.j1angvei.cbnews.di.module.sub.BookmarkModule;
import cn.j1angvei.cbnews.di.module.sub.HeadlineModule;
import cn.j1angvei.cbnews.di.module.sub.ReviewModule;
import cn.j1angvei.cbnews.di.scope.PerActivity;
import dagger.Component;

/**
 * Created by Wayne on 2016/6/15.
 * component for all {@link android.support.v7.app.AppCompatActivity}
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(NewsActivity activity);

    void inject(ContentActivity activity);

    void inject(CommentActivity activity);

    void inject(TopicNewsActivity activity);

    //expose variable to sub component
    FragmentComponent fragmentComponent(FragmentModule module);

    ArticleComponent articleComponent(ArticleModule module, FragmentModule fragmentModule);

    HeadlineComponent headlineComponent(HeadlineModule module, FragmentModule fragmentModule);

    ReviewComponent reviewComponent(ReviewModule module, FragmentModule fragmentModule);

    BookmarkComponent bookmarkComponent(BookmarkModule module, FragmentModule fragmentModule);

}
