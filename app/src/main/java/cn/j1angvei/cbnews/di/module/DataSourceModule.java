package cn.j1angvei.cbnews.di.module;

import javax.inject.Named;
import javax.inject.Singleton;

import cn.j1angvei.cbnews.bean.Article;
import cn.j1angvei.cbnews.bean.Bookmark;
import cn.j1angvei.cbnews.bean.Headline;
import cn.j1angvei.cbnews.bean.Review;
import cn.j1angvei.cbnews.converter.ArticleConverter;
import cn.j1angvei.cbnews.converter.BookmarkConverter;
import cn.j1angvei.cbnews.converter.HeadlineConverter;
import cn.j1angvei.cbnews.converter.ReviewConverter;
import cn.j1angvei.cbnews.data.local.NewsLocalSource;
import cn.j1angvei.cbnews.data.local.helper.ArticleDbHelper;
import cn.j1angvei.cbnews.data.local.helper.BookmarkDbHelper;
import cn.j1angvei.cbnews.data.local.helper.HeadlineDbHelper;
import cn.j1angvei.cbnews.data.local.helper.ReviewDbHelper;
import cn.j1angvei.cbnews.data.remote.NewsRemoteSource;
import cn.j1angvei.cbnews.data.remote.api.CBApiWrapper;
import cn.j1angvei.cbnews.util.NetworkUtil;
import dagger.Module;
import dagger.Provides;

import static cn.j1angvei.cbnews.bean.News.Type.ARTICLE;
import static cn.j1angvei.cbnews.bean.News.Type.BOOKMARK;
import static cn.j1angvei.cbnews.bean.News.Type.HEADLINE;
import static cn.j1angvei.cbnews.bean.News.Type.REVIEW;

/**
 * Created by Wayne on 2016/8/20.
 */
@Module
public class DataSourceModule {
    @Provides
    @Singleton
    @Named(ARTICLE)
    NewsLocalSource<Article> provideArticleNewsLocalSource(ArticleDbHelper helper) {
        return new NewsLocalSource<>(helper);
    }

    @Provides
    @Singleton
    @Named(REVIEW)
    NewsLocalSource<Review> provideReviewNewsLocalSource(ReviewDbHelper helper) {
        return new NewsLocalSource<>(helper);
    }

    @Provides
    @Singleton
    @Named(HEADLINE)
    NewsLocalSource<Headline> provideHeadlineNewsLocalSource(HeadlineDbHelper helper) {
        return new NewsLocalSource<>(helper);
    }

    @Provides
    @Singleton
    @Named(BOOKMARK)
    NewsLocalSource<Bookmark> provideBookmarkLocalSource(BookmarkDbHelper helper) {
        return new NewsLocalSource<>(helper);
    }

    @Provides
    @Singleton
    @Named(ARTICLE)
    NewsRemoteSource<Article> provideArticleNewsRemoteSource(CBApiWrapper wrapper, ArticleConverter converter, NetworkUtil networkUtil) {
        return new NewsRemoteSource<>(wrapper, converter, networkUtil);
    }

    @Provides
    @Singleton
    @Named(REVIEW)
    NewsRemoteSource<Review> provideReviewNewsRemoteSource(CBApiWrapper wrapper, ReviewConverter converter, NetworkUtil networkUtil) {
        return new NewsRemoteSource<>(wrapper, converter, networkUtil);
    }

    @Provides
    @Singleton
    @Named(HEADLINE)
    NewsRemoteSource<Headline> provideHeadlineNewsRemoteSource(CBApiWrapper wrapper, HeadlineConverter converter, NetworkUtil networkUtil) {
        return new NewsRemoteSource<>(wrapper, converter, networkUtil);
    }

    @Provides
    @Singleton
    @Named(BOOKMARK)
    NewsRemoteSource<Bookmark> provideBookmarkRemoteSource(CBApiWrapper wrapper, BookmarkConverter converter, NetworkUtil networkUtil) {
        return new NewsRemoteSource<>(wrapper, converter, networkUtil);
    }
}
