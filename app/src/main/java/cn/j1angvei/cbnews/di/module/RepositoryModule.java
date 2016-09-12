package cn.j1angvei.cbnews.di.module;

import javax.inject.Singleton;

import cn.j1angvei.cbnews.bean.Article;
import cn.j1angvei.cbnews.bean.Bookmark;
import cn.j1angvei.cbnews.bean.Comments;
import cn.j1angvei.cbnews.bean.Content;
import cn.j1angvei.cbnews.bean.Headline;
import cn.j1angvei.cbnews.bean.Review;
import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.base.LocalSource;
import cn.j1angvei.cbnews.base.RemoteSource;
import cn.j1angvei.cbnews.newscomments.CommentRepository;
import cn.j1angvei.cbnews.newscontent.ContentRepository;
import cn.j1angvei.cbnews.newslist.NewsRepository;
import cn.j1angvei.cbnews.base.Repository;
import cn.j1angvei.cbnews.alltopic.TopicRepository;
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
 * Created by Wayne on 2016/8/20.
 */
@Module(includes = {LocalModule.class, RemoteModule.class})
public abstract class RepositoryModule {
    @Provides
    @Singleton
    @QArticle
    static Repository<Article> provideArticleRepository(@QArticle LocalSource<Article> local, @QArticle RemoteSource<Article> remote) {
        return new NewsRepository<>(local, remote);
    }

    @Provides
    @Singleton
    @QReview
    static Repository<Review> provideReviewRepository(@QReview LocalSource<Review> local, @QReview RemoteSource<Review> remote) {
        return new NewsRepository<>(local, remote);
    }

    @Provides
    @Singleton
    @QHeadline
    static Repository<Headline> provideHeadlineRepository(@QHeadline LocalSource<Headline> local, @QHeadline RemoteSource<Headline> remote) {
        return new NewsRepository<>(local, remote);
    }

    @Provides
    @Singleton
    @QBookmark
    static Repository<Bookmark> provideBookmarkRepository(@QBookmark LocalSource<Bookmark> local, @QBookmark RemoteSource<Bookmark> remote) {
        return new NewsRepository<>(local, remote);
    }

    @Binds
    @QCmt
    abstract Repository<Comments> bindsCmtRepo(CommentRepository repository);

    @Binds
    @QContent
    abstract Repository<Content> bindsContentRepo(ContentRepository repository);

    @Binds
    @QTopic
    abstract Repository<Topic> bindsTopicRepo(TopicRepository repository);
}
