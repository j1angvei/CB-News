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
import dagger.Module;
import dagger.Provides;

/**
 * Created by Wayne on 2016/8/20.
 */
@Module
public class RepositoryModule {
    @Provides
    @Singleton
    @Named("d_article")
    NewsRepository<Article> provideArticleRepository(@Named("l_article") NewsLocalSource<Article> local, @Named("r_article") NewsRemoteSource<Article> remote) {
        return new NewsRepository<>(local, remote);
    }

    @Provides
    @Singleton
    @Named("d_review")
    NewsRepository<Review> provideReviewRepository(@Named("l_review") NewsLocalSource<Review> local, @Named("r_review") NewsRemoteSource<Review> remote) {
        return new NewsRepository<>(local, remote);
    }

    @Provides
    @Singleton
    @Named("d_headline")
    NewsRepository<Headline> provideHeadlineRepository(@Named("l_headline") NewsLocalSource<Headline> local, @Named("r_headline") NewsRemoteSource<Headline> remote) {
        return new NewsRepository<>(local, remote);
    }

    @Provides
    @Singleton
    @Named("d_bookmark")
    NewsRepository<Bookmark> provideBookmarkRepository(@Named("l_bookmark") NewsLocalSource<Bookmark> local, @Named("r_bookmark") NewsRemoteSource<Bookmark> remote) {
        return new NewsRepository<>(local, remote);
    }

}
