package cn.j1angvei.cnbetareader.di.module;

import javax.inject.Named;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.bean.Bookmark;
import cn.j1angvei.cnbetareader.bean.Headline;
import cn.j1angvei.cnbetareader.bean.Review;
import cn.j1angvei.cnbetareader.converter.ArticleConverter;
import cn.j1angvei.cnbetareader.converter.BookmarkConverter;
import cn.j1angvei.cnbetareader.converter.HeadlineConverter;
import cn.j1angvei.cnbetareader.converter.ReviewConverter;
import cn.j1angvei.cnbetareader.data.local.NewsLocalSource;
import cn.j1angvei.cnbetareader.data.local.helper.ArticleDbHelper;
import cn.j1angvei.cnbetareader.data.local.helper.BookmarkDbHelper;
import cn.j1angvei.cnbetareader.data.local.helper.HeadlineDbHelper;
import cn.j1angvei.cnbetareader.data.local.helper.ReviewDbHelper;
import cn.j1angvei.cnbetareader.data.remote.NewsRemoteSource;
import cn.j1angvei.cnbetareader.data.remote.api.CnbetaApi;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Wayne on 2016/8/20.
 */
@Module
public class DataSourceModule {
    @Provides
    @Singleton
    @Named("l_article")
    NewsLocalSource<Article> provideArticleNewsLocalSource(ArticleDbHelper helper) {
        return new NewsLocalSource<>(helper);
    }

    @Provides
    @Singleton
    @Named("l_review")
    NewsLocalSource<Review> provideReviewNewsLocalSource(ReviewDbHelper helper) {
        return new NewsLocalSource<>(helper);
    }

    @Provides
    @Singleton
    @Named("l_headline")
    NewsLocalSource<Headline> provideHeadlineNewsLocalSource(HeadlineDbHelper helper) {
        return new NewsLocalSource<>(helper);
    }

    @Provides
    @Singleton
    @Named("l_bookmark")
    NewsLocalSource<Bookmark> provideBookmarkLocalSource(BookmarkDbHelper helper) {
        return new NewsLocalSource<>(helper);
    }

    @Provides
    @Singleton
    @Named("r_article")
    NewsRemoteSource<Article> provideArticleNewsRemoteSource(CnbetaApi api, ArticleConverter converter) {
        return new NewsRemoteSource<>(api, converter);
    }

    @Provides
    @Singleton
    @Named("r_review")
    NewsRemoteSource<Review> provideReviewNewsRemoteSource(CnbetaApi api, ReviewConverter converter) {
        return new NewsRemoteSource<>(api, converter);
    }

    @Provides
    @Singleton
    @Named("r_headline")
    NewsRemoteSource<Headline> provideHeadlineNewsRemoteSource(CnbetaApi api, HeadlineConverter converter) {
        return new NewsRemoteSource<>(api, converter);
    }

    @Provides
    @Singleton
    @Named("r_bookmark")
    NewsRemoteSource<Bookmark> provideBookmarkRemoteSource(CnbetaApi api, BookmarkConverter converter) {
        return new NewsRemoteSource<>(api, converter);
    }
}
