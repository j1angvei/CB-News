package cn.j1angvei.cnbetareader.di.component;

import android.app.Application;

import javax.inject.Named;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.bean.Bookmark;
import cn.j1angvei.cnbetareader.bean.Headline;
import cn.j1angvei.cnbetareader.bean.Review;
import cn.j1angvei.cnbetareader.data.remote.api.CnbetaApi;
import cn.j1angvei.cnbetareader.data.repository.AllTopicsRepository;
import cn.j1angvei.cnbetareader.data.repository.CommentsRepository;
import cn.j1angvei.cnbetareader.data.repository.ContentRepository;
import cn.j1angvei.cnbetareader.data.repository.MyTopicsRepository;
import cn.j1angvei.cnbetareader.data.repository.NewsRepository;
import cn.j1angvei.cnbetareader.di.module.ApplicationModule;
import cn.j1angvei.cnbetareader.di.module.DataSourceModule;
import cn.j1angvei.cnbetareader.di.module.RepositoryModule;
import cn.j1angvei.cnbetareader.util.ApiUtil;
import cn.j1angvei.cnbetareader.util.PrefsUtil;
import dagger.Component;

import static cn.j1angvei.cnbetareader.bean.News.Type.ARTICLE;
import static cn.j1angvei.cnbetareader.bean.News.Type.BOOKMARK;
import static cn.j1angvei.cnbetareader.bean.News.Type.HEADLINE;
import static cn.j1angvei.cnbetareader.bean.News.Type.REVIEW;

/**
 * Created by Wayne on 2016/6/15.
 * component specific for {@link cn.j1angvei.cnbetareader.CBApplication}
 */
@Singleton
@Component(modules = {ApplicationModule.class, RepositoryModule.class, DataSourceModule.class})
public interface ApplicationComponent {
    //expose to sub activity graph
    Application application();

    @Named(ARTICLE)
    NewsRepository<Article> articleRepository();

    @Named(REVIEW)
    NewsRepository<Review> reviewRepository();

    @Named(HEADLINE)
    NewsRepository<Headline> headlineRepository();

    @Named(BOOKMARK)
    NewsRepository<Bookmark> bookmarkRepository();

    MyTopicsRepository myTopicsRepository();

    AllTopicsRepository exploreRepository();

    ContentRepository contentRepository();

    CommentsRepository commentsRepository();

    CnbetaApi cnbetaApi();

    PrefsUtil prefUtil();

    ApiUtil apiUtil();

}
