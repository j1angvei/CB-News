package cn.j1angvei.cbnews.di.module;

import javax.inject.Singleton;

import cn.j1angvei.cbnews.bean.Article;
import cn.j1angvei.cbnews.bean.Bookmark;
import cn.j1angvei.cbnews.bean.Comments;
import cn.j1angvei.cbnews.bean.Content;
import cn.j1angvei.cbnews.bean.Headline;
import cn.j1angvei.cbnews.bean.Review;
import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.converter.ArticleConverter;
import cn.j1angvei.cbnews.converter.BookmarkConverter;
import cn.j1angvei.cbnews.converter.HeadlineConverter;
import cn.j1angvei.cbnews.converter.ReviewConverter;
import cn.j1angvei.cbnews.data.remote.CmtRemoteSource;
import cn.j1angvei.cbnews.data.remote.ContentRemoteSource;
import cn.j1angvei.cbnews.data.remote.NewsRemoteSource;
import cn.j1angvei.cbnews.data.remote.RemoteSource;
import cn.j1angvei.cbnews.data.remote.TopicRemoteSource;
import cn.j1angvei.cbnews.data.remote.api.CBApiWrapper;
import cn.j1angvei.cbnews.di.qualifier.QArticle;
import cn.j1angvei.cbnews.di.qualifier.QBookmark;
import cn.j1angvei.cbnews.di.qualifier.QCmt;
import cn.j1angvei.cbnews.di.qualifier.QContent;
import cn.j1angvei.cbnews.di.qualifier.QHeadline;
import cn.j1angvei.cbnews.di.qualifier.QReview;
import cn.j1angvei.cbnews.di.qualifier.QTopic;
import cn.j1angvei.cbnews.util.NetworkUtil;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Wayne on 2016/9/10.
 */
@Module(includes = ConverterModule.class)
public abstract class RemoteModule {
    @Binds
    @QCmt
    abstract RemoteSource<Comments> bindsMR(CmtRemoteSource source);
    @Binds
    @QContent
    abstract RemoteSource<Content> bindsCR(ContentRemoteSource source);
    @Binds
    @QTopic
    abstract RemoteSource<Topic> bindsTR(TopicRemoteSource source);

    @Provides
    @Singleton
    @QArticle
    static RemoteSource<Article> provideArticleNewsRemoteSource(CBApiWrapper wrapper, ArticleConverter converter, NetworkUtil networkUtil) {
        return new NewsRemoteSource<>(wrapper, converter, networkUtil);
    }

    @Provides
    @Singleton
    @QReview
    static RemoteSource<Review> provideReviewNewsRemoteSource(CBApiWrapper wrapper, ReviewConverter converter, NetworkUtil networkUtil) {
        return new NewsRemoteSource<>(wrapper, converter, networkUtil);
    }

    @Provides
    @Singleton
    @QHeadline
    static RemoteSource<Headline> provideHeadlineNewsRemoteSource(CBApiWrapper wrapper, HeadlineConverter converter, NetworkUtil networkUtil) {
        return new NewsRemoteSource<>(wrapper, converter, networkUtil);
    }

    @Provides
    @Singleton
    @QBookmark
    static RemoteSource<Bookmark> provideBookmarkRemoteSource(CBApiWrapper wrapper, BookmarkConverter converter, NetworkUtil networkUtil) {
        return new NewsRemoteSource<>(wrapper, converter, networkUtil);
    }
}
