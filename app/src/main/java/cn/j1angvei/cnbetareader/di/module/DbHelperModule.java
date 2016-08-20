package cn.j1angvei.cnbetareader.di.module;

import android.app.Application;

import javax.inject.Named;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.bean.Bookmark;
import cn.j1angvei.cnbetareader.bean.Headline;
import cn.j1angvei.cnbetareader.bean.Review;
import cn.j1angvei.cnbetareader.data.local.helper.DbHelper;
import cn.j1angvei.cnbetareader.data.local.helper.ArticleDbHelper;
import cn.j1angvei.cnbetareader.data.local.helper.BookmarkDbHelper;
import cn.j1angvei.cnbetareader.data.local.helper.HeadlineDbHelper;
import cn.j1angvei.cnbetareader.data.local.helper.ReviewDbHelper;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Wayne on 2016/8/20.
 */
@Module
public class DbHelperModule {
    @Provides
    @Singleton
    @Named("h_article")
    DbHelper<Article> provideArticleDbHelper(Application application) {
        return new ArticleDbHelper(application);
    }

    @Provides
    @Singleton
    @Named("h_review")
    DbHelper<Review> provideReviewDbHelper(Application application) {
        return new ReviewDbHelper(application);
    }

    @Provides
    @Singleton
    @Named("h_headline")
    DbHelper<Headline> provideHeadlineDbHelper(Application application) {
        return new HeadlineDbHelper(application);
    }

    @Provides
    @Singleton
    @Named("h_bookmark")
    DbHelper<Bookmark> provideBookmarkDbHelper(Application application) {
        return new BookmarkDbHelper(application);
    }
}
