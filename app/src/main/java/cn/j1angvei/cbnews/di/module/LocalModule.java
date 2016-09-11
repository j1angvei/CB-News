package cn.j1angvei.cbnews.di.module;

import javax.inject.Singleton;

import cn.j1angvei.cbnews.bean.Article;
import cn.j1angvei.cbnews.bean.Bookmark;
import cn.j1angvei.cbnews.bean.Comments;
import cn.j1angvei.cbnews.bean.Content;
import cn.j1angvei.cbnews.bean.Headline;
import cn.j1angvei.cbnews.bean.Review;
import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.data.local.CommentLocalSource;
import cn.j1angvei.cbnews.data.local.ContentLocalSource;
import cn.j1angvei.cbnews.data.local.LocalSource;
import cn.j1angvei.cbnews.data.local.NewsLocalSource;
import cn.j1angvei.cbnews.data.local.TopicLocalSource;
import cn.j1angvei.cbnews.data.local.helper.DbHelper;
import cn.j1angvei.cbnews.di.qualifier.QArticle;
import cn.j1angvei.cbnews.di.qualifier.QBookmark;
import cn.j1angvei.cbnews.di.qualifier.QCmt;
import cn.j1angvei.cbnews.di.qualifier.QContent;
import cn.j1angvei.cbnews.di.qualifier.QHeadline;
import cn.j1angvei.cbnews.di.qualifier.QReview;
import cn.j1angvei.cbnews.di.qualifier.QTopic;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Wayne on 2016/9/10.
 */
@Module(includes = HelperModule.class)
public abstract class LocalModule {
    @Binds
    @QContent
    abstract LocalSource<Content> bindsCL(ContentLocalSource source);

    @Binds
    @QCmt
    abstract LocalSource<Comments> bindsML(CommentLocalSource source);

    @Binds
    @QTopic
    abstract LocalSource<Topic> bindsTL(TopicLocalSource source);

    @Singleton
    @Provides
    @QArticle
    static LocalSource<Article> provideAL(@QArticle DbHelper<Article> helper) {
        return new NewsLocalSource<>(helper);
    }

    @Provides
    @Singleton
    @QReview
    static LocalSource<Review> provideReviewNewsLocalSource(@QReview DbHelper<Review> helper) {
        return new NewsLocalSource<>(helper);
    }

    @Provides
    @Singleton
    @QHeadline
    static LocalSource<Headline> provideHeadlineNewsLocalSource(@QHeadline DbHelper<Headline> helper) {
        return new NewsLocalSource<>(helper);
    }

    @Provides
    @Singleton
    @QBookmark
    static LocalSource<Bookmark> provideBookmarkLocalSource(@QBookmark DbHelper<Bookmark> helper) {
        return new NewsLocalSource<>(helper);
    }
}
