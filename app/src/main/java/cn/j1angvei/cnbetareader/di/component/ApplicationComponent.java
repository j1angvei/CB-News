package cn.j1angvei.cnbetareader.di.component;

import android.app.Application;

import javax.inject.Named;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.bean.Headline;
import cn.j1angvei.cnbetareader.bean.Review;
import cn.j1angvei.cnbetareader.data.DataRepository;
import cn.j1angvei.cnbetareader.di.module.ApplicationModule;
import cn.j1angvei.cnbetareader.di.module.DataModule;
import dagger.Component;

/**
 * Created by Wayne on 2016/6/15.
 */
@Singleton
@Component(modules = {ApplicationModule.class, DataModule.class})
public interface ApplicationComponent {

    //expose to sub activity graph
    Application application();

    @Named("d_article")
    DataRepository<Article> articleRepository();

    @Named("d_review")
    DataRepository<Review> reviewRepository();

    @Named("d_headline")
    DataRepository<Headline> headlineRepository();

}
