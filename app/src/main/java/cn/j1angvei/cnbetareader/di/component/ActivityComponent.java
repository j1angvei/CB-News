package cn.j1angvei.cnbetareader.di.component;

import android.app.Activity;

import cn.j1angvei.cnbetareader.di.module.ActivityModule;
import cn.j1angvei.cnbetareader.di.module.ArticlesModule;
import cn.j1angvei.cnbetareader.di.module.HeadlinesModule;
import cn.j1angvei.cnbetareader.di.module.ReviewModule;
import cn.j1angvei.cnbetareader.di.module.TopicsModule;
import cn.j1angvei.cnbetareader.di.scope.PerActivity;
import cn.j1angvei.cnbetareader.activity.NewsListActivity;
import dagger.Component;

/**
 * Created by Wayne on 2016/6/15.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(NewsListActivity newsListActivity);

    //expose variable to sub-graph
    Activity activity();

    ArticlesComponent articlesComponent(ArticlesModule articlesModule);

    HeadlinesComponent headlinesComponent(HeadlinesModule headlinesModule);

    ReviewsComponent reviewsComponent(ReviewModule reviewModule);

    TopicsComponent topicsComponent(TopicsModule topicsModule);

}
