package cn.j1angvei.cnbetareader.di.module;

import com.google.gson.Gson;

import javax.inject.Named;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.bean.Bookmark;
import cn.j1angvei.cnbetareader.bean.Comments;
import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.bean.Headline;
import cn.j1angvei.cnbetareader.bean.Review;
import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.converter.ArticleConverter;
import cn.j1angvei.cnbetareader.converter.BookmarkConverter;
import cn.j1angvei.cnbetareader.converter.CommentsConverter;
import cn.j1angvei.cnbetareader.converter.ContentConverter;
import cn.j1angvei.cnbetareader.converter.Converter;
import cn.j1angvei.cnbetareader.converter.HeadlineConverter;
import cn.j1angvei.cnbetareader.converter.ReviewConverter;
import cn.j1angvei.cnbetareader.converter.TopicConverter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Wayne on 2016/8/20.
 */
@Module
public class ConverterModule {
    @Provides
    @Singleton
    @Named("c_article")
    Converter<Article> provideArticleConverter(Gson gson) {
        return new ArticleConverter(gson);
    }

    @Provides
    @Singleton
    @Named("c_review")
    Converter<Review> provideReviewConverter(Gson gson) {
        return new ReviewConverter(gson);
    }

    @Provides
    @Singleton
    @Named("c_headline")
    Converter<Headline> provideHeadlineConverter(Gson gson) {
        return new HeadlineConverter(gson);
    }

    @Provides
    @Singleton
    @Named("c_topic")
    Converter<Topic> provideTopicConverter() {
        return new TopicConverter();
    }

    @Provides
    @Singleton
    @Named("c_content")
    Converter<Content> provideContentConverter() {
        return new ContentConverter();
    }

    @Provides
    @Singleton
    @Named("c_bookmark")
    Converter<Bookmark> provideBookmarkConverter(Gson gson) {
        return new BookmarkConverter(gson);
    }

    @Provides
    @Singleton
    @Named("c_comments")
    Converter<Comments> providesCommentsConverter(Gson gson) {
        return new CommentsConverter(gson);
    }

}
