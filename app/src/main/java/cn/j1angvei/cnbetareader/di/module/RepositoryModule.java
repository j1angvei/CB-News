package cn.j1angvei.cnbetareader.di.module;

import javax.inject.Named;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.bean.Bookmark;
import cn.j1angvei.cnbetareader.bean.Headline;
import cn.j1angvei.cnbetareader.bean.Review;
import cn.j1angvei.cnbetareader.data.local.NewsLocalSource;
import cn.j1angvei.cnbetareader.data.remote.NewsRemoteSource;
import cn.j1angvei.cnbetareader.data.repository.NewsRepository;
import cn.j1angvei.cnbetareader.util.NetworkUtil;
import dagger.Module;
import dagger.Provides;

import static cn.j1angvei.cnbetareader.bean.News.Type.ARTICLE;
import static cn.j1angvei.cnbetareader.bean.News.Type.BOOKMARK;
import static cn.j1angvei.cnbetareader.bean.News.Type.HEADLINE;
import static cn.j1angvei.cnbetareader.bean.News.Type.REVIEW;

/**
 * Created by Wayne on 2016/8/20.
 */
@Module
public class RepositoryModule {
    @Provides
    @Singleton
    @Named(ARTICLE)
    NewsRepository<Article> provideArticleRepository(@Named(ARTICLE) NewsLocalSource<Article> local, @Named(ARTICLE) NewsRemoteSource<Article> remote, NetworkUtil util) {
        return new NewsRepository<>(local, remote, util);
    }

    @Provides
    @Singleton
    @Named(REVIEW)
    NewsRepository<Review> provideReviewRepository(@Named(REVIEW) NewsLocalSource<Review> local, @Named(REVIEW) NewsRemoteSource<Review> remote, NetworkUtil util) {
        return new NewsRepository<>(local, remote, util);
    }

    @Provides
    @Singleton
    @Named(HEADLINE)
    NewsRepository<Headline> provideHeadlineRepository(@Named(HEADLINE) NewsLocalSource<Headline> local, @Named(HEADLINE) NewsRemoteSource<Headline> remote, NetworkUtil util) {
        return new NewsRepository<>(local, remote, util);
    }

    @Provides
    @Singleton
    @Named(BOOKMARK)
    NewsRepository<Bookmark> provideBookmarkRepository(@Named(BOOKMARK) NewsLocalSource<Bookmark> local, @Named(BOOKMARK) NewsRemoteSource<Bookmark> remote, NetworkUtil util) {
        return new NewsRepository<>(local, remote, util);
    }
}
