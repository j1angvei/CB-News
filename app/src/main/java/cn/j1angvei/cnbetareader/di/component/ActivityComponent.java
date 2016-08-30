package cn.j1angvei.cnbetareader.di.component;

import cn.j1angvei.cnbetareader.activity.CommentsActivity;
import cn.j1angvei.cnbetareader.activity.ContentActivity;
import cn.j1angvei.cnbetareader.activity.NewsActivity;
import cn.j1angvei.cnbetareader.di.module.ActivityModule;
import cn.j1angvei.cnbetareader.di.module.FragmentModule;
import cn.j1angvei.cnbetareader.di.module.sub.ArticleModule;
import cn.j1angvei.cnbetareader.di.module.sub.BookmarkModule;
import cn.j1angvei.cnbetareader.di.module.sub.HeadlineModule;
import cn.j1angvei.cnbetareader.di.module.sub.ReviewModule;
import cn.j1angvei.cnbetareader.di.scope.PerActivity;
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

    void inject(CommentsActivity activity);

    //expose variable to sub component
    FragmentComponent fragmentComponent(FragmentModule module);

    ArticleComponent articleComponent(ArticleModule module, FragmentModule fragmentModule);

    HeadlineComponent headlineComponent(HeadlineModule module, FragmentModule fragmentModule);

    ReviewComponent reviewComponent(ReviewModule module, FragmentModule fragmentModule);

    BookmarkComponent bookmarkComponent(BookmarkModule module, FragmentModule fragmentModule);

}
