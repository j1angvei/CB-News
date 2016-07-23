package cn.j1angvei.cnbetareader.di.component;

import android.app.Activity;

import cn.j1angvei.cnbetareader.activity.ContentActivity;
import cn.j1angvei.cnbetareader.di.module.ActivityModule;
import cn.j1angvei.cnbetareader.di.module.sub.NestedTopicsModule;
import cn.j1angvei.cnbetareader.di.module.sub.ArticleModule;
import cn.j1angvei.cnbetareader.di.module.sub.HeadlineModule;
import cn.j1angvei.cnbetareader.di.module.sub.ReviewModule;
import cn.j1angvei.cnbetareader.di.scope.PerActivity;
import cn.j1angvei.cnbetareader.activity.NewsActivity;
import dagger.Component;

/**
 * Created by Wayne on 2016/6/15.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(NewsActivity activity);

    void inject(ContentActivity activity);

    //expose variable to sub-graph
    Activity activity();

    ArticleComponent articleComponent(ArticleModule module);

    HeadlineComponent headlineComponent(HeadlineModule module);

    ReviewComponent reviewComponent(ReviewModule module);

    NestedTopicsComponent myTopicsComponent(NestedTopicsModule module);

}
