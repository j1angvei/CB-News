package cn.j1angvei.cbnews.di.component;

import android.app.Application;

import javax.inject.Named;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.CBApplication;
import cn.j1angvei.cbnews.bean.Article;
import cn.j1angvei.cbnews.bean.Bookmark;
import cn.j1angvei.cbnews.bean.Headline;
import cn.j1angvei.cbnews.bean.Review;
import cn.j1angvei.cbnews.converter.MyTopicsConverter;
import cn.j1angvei.cbnews.data.remote.api.CBApiWrapper;
import cn.j1angvei.cbnews.data.repository.AllTopicsRepository;
import cn.j1angvei.cbnews.data.repository.CommentsRepository;
import cn.j1angvei.cbnews.data.repository.ContentRepository;
import cn.j1angvei.cbnews.data.repository.MyTopicsRepository;
import cn.j1angvei.cbnews.data.repository.NewsRepository;
import cn.j1angvei.cbnews.di.module.ApplicationModule;
import cn.j1angvei.cbnews.di.module.DataSourceModule;
import cn.j1angvei.cbnews.di.module.RepositoryModule;
import cn.j1angvei.cbnews.util.AppUtil;
import cn.j1angvei.cbnews.util.NetworkUtil;
import cn.j1angvei.cbnews.util.PrefsUtil;
import dagger.Component;

import static cn.j1angvei.cbnews.bean.News.Type.ARTICLE;
import static cn.j1angvei.cbnews.bean.News.Type.BOOKMARK;
import static cn.j1angvei.cbnews.bean.News.Type.HEADLINE;
import static cn.j1angvei.cbnews.bean.News.Type.REVIEW;

/**
 * Created by Wayne on 2016/6/15.
 * component specific for {@link cn.j1angvei.cbnews.CBApplication}
 */
@Singleton
@Component(modules = {ApplicationModule.class, RepositoryModule.class, DataSourceModule.class})
public interface ApplicationComponent {

    void inject(CBApplication application);

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

    PrefsUtil prefUtil();

    CBApiWrapper apiHelper();

    MyTopicsConverter myTopicsConverter();

    NetworkUtil networkUtil();

    AppUtil appUtil();
}
