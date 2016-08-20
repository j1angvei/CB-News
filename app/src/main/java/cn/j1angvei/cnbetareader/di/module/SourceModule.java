package cn.j1angvei.cnbetareader.di.module;

import javax.inject.Named;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.bean.Bookmark;
import cn.j1angvei.cnbetareader.bean.Headline;
import cn.j1angvei.cnbetareader.bean.Review;
import cn.j1angvei.cnbetareader.converter.Converter;
import cn.j1angvei.cnbetareader.data.local.NewsLocalSource;
import cn.j1angvei.cnbetareader.data.local.helper.DbHelper;
import cn.j1angvei.cnbetareader.data.remote.NewsRemoteSource;
import cn.j1angvei.cnbetareader.data.remote.api.CnbetaApi;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Wayne on 2016/8/20.
 */
@Module
public class SourceModule {
    @Provides
    @Singleton
    @Named("l_article")
    NewsLocalSource<Article> provideArticleNewsLocalSource(@Named("h_article") DbHelper<Article> helper) {
        return new NewsLocalSource<>(helper);
    }

    @Provides
    @Singleton
    @Named("l_review")
    NewsLocalSource<Review> provideReviewNewsLocalSource(@Named("h_review") DbHelper<Review> helper) {
        return new NewsLocalSource<>(helper);
    }

    @Provides
    @Singleton
    @Named("l_headline")
    NewsLocalSource<Headline> provideHeadlineNewsLocalSource(@Named("h_headline") DbHelper<Headline> helper) {
        return new NewsLocalSource<>(helper);
    }

    @Provides
    @Singleton
    @Named("l_bookmark")
    NewsLocalSource<Bookmark> provideBookmarkLocalSource(@Named("h_bookmark") DbHelper<Bookmark> helper) {
        return new NewsLocalSource<>(helper);
    }

    @Provides
    @Singleton
    @Named("r_article")
    NewsRemoteSource<Article> provideArticleNewsRemoteSource(CnbetaApi api, @Named("c_article") Converter<Article> converter) {
        return new NewsRemoteSource<>(api, converter);
    }

    @Provides
    @Singleton
    @Named("r_review")
    NewsRemoteSource<Review> provideReviewNewsRemoteSource(CnbetaApi api, @Named("c_review") Converter<Review> converter) {
        return new NewsRemoteSource<>(api, converter);
    }

    @Provides
    @Singleton
    @Named("r_headline")
    NewsRemoteSource<Headline> provideHeadlineNewsRemoteSource(CnbetaApi api, @Named("c_headline") Converter<Headline> converter) {
        return new NewsRemoteSource<>(api, converter);
    }

    @Provides
    @Singleton
    @Named("r_bookmark")
    NewsRemoteSource<Bookmark> provideBookmarkRemoteSource(CnbetaApi api, @Named("c_bookmark") Converter<Bookmark> converter) {
        return new NewsRemoteSource<>(api, converter);
    }
}
