package cn.j1angvei.cbnews.di.module;

import cn.j1angvei.cbnews.bean.Article;
import cn.j1angvei.cbnews.bean.Bookmark;
import cn.j1angvei.cbnews.bean.Comments;
import cn.j1angvei.cbnews.bean.Content;
import cn.j1angvei.cbnews.bean.Headline;
import cn.j1angvei.cbnews.bean.Review;
import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.converter.ArticleConverter;
import cn.j1angvei.cbnews.converter.BookmarkConverter;
import cn.j1angvei.cbnews.converter.CommentsConverter;
import cn.j1angvei.cbnews.converter.ContentConverter;
import cn.j1angvei.cbnews.converter.Converter;
import cn.j1angvei.cbnews.converter.HeadlineConverter;
import cn.j1angvei.cbnews.converter.ReviewConverter;
import cn.j1angvei.cbnews.converter.TopicConverter;
import cn.j1angvei.cbnews.di.qualifier.QArticle;
import cn.j1angvei.cbnews.di.qualifier.QBookmark;
import cn.j1angvei.cbnews.di.qualifier.QCmt;
import cn.j1angvei.cbnews.di.qualifier.QContent;
import cn.j1angvei.cbnews.di.qualifier.QHeadline;
import cn.j1angvei.cbnews.di.qualifier.QReview;
import cn.j1angvei.cbnews.di.qualifier.QTopic;
import dagger.Binds;
import dagger.Module;

/**
 * Created by Wayne on 2016/9/10.
 */
@Module
public abstract class ConverterModule {
    @Binds
    @QArticle
    abstract Converter<Article> bindsAC(ArticleConverter converter);

    @Binds
    @QReview
    abstract Converter<Review> bindsRC(ReviewConverter converter);

    @Binds
    @QHeadline
    abstract Converter<Headline> bindsHC(HeadlineConverter converter);

    @Binds
    @QBookmark
    abstract Converter<Bookmark> bindsBC(BookmarkConverter converter);

    @Binds
    @QCmt
    abstract Converter<Comments> bindsMC(CommentsConverter converter);

    @Binds
    @QContent
    abstract Converter<Content> bindsCC(ContentConverter converter);

    @Binds
    @QTopic
    abstract Converter<Topic> bindsTC(TopicConverter converter);

}
