package cn.j1angvei.cnbetareader.di.module;

import android.app.Application;

import com.google.gson.Gson;

import javax.inject.Named;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.bean.Headline;
import cn.j1angvei.cnbetareader.bean.Review;
import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.converter.ArticleConverter;
import cn.j1angvei.cnbetareader.converter.ContentConverter;
import cn.j1angvei.cnbetareader.converter.Converter;
import cn.j1angvei.cnbetareader.converter.HeadlineConverter;
import cn.j1angvei.cnbetareader.converter.ReviewConverter;
import cn.j1angvei.cnbetareader.converter.TopicConverter;
import cn.j1angvei.cnbetareader.data.local.NewsLocalSource;
import cn.j1angvei.cnbetareader.data.remote.CnbetaApi;
import cn.j1angvei.cnbetareader.data.remote.NewsRemoteSource;
import cn.j1angvei.cnbetareader.data.repository.NewsRepository;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Wayne on 2016/7/23.
 */
@Module
public class DataModule {
    @Provides
    @Singleton
    @Named("c_article")
    Converter<Article> provideArticleConverter(Gson gson, String url) {
        return new ArticleConverter(gson, url);
    }

    @Provides
    @Singleton
    @Named("c_review")
    Converter<Review> provideReviewConverter(Gson gson, String url) {
        return new ReviewConverter(gson, url);
    }


    @Provides
    @Singleton
    @Named("c_headline")
    Converter<Headline> provideHeadlineConverter(Gson gson, String url) {
        return new HeadlineConverter(gson, url);
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
    Converter<Content> provideContentConverter(String baseUrl) {
        return new ContentConverter(baseUrl);
    }

    @Provides
    @Singleton
    @Named("l_article")
    NewsLocalSource<Article> provideArticleNewsLocalSource(Application application) {
        return new NewsLocalSource<>(application);
    }

    @Provides
    @Singleton
    @Named("l_review")
    NewsLocalSource<Review> provideReviewNewsLocalSource(Application application) {
        return new NewsLocalSource<>(application);
    }

    @Provides
    @Singleton
    @Named("l_headline")
    NewsLocalSource<Headline> provideHeadlineNewsLocalSource(Application application) {
        return new NewsLocalSource<>(application);
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

}
