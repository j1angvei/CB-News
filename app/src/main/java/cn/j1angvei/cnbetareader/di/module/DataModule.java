package cn.j1angvei.cnbetareader.di.module;

import com.google.gson.Gson;

import javax.inject.Named;
import javax.inject.Singleton;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.bean.Headline;
import cn.j1angvei.cnbetareader.bean.Review;
import cn.j1angvei.cnbetareader.converter.ArticleConverter;
import cn.j1angvei.cnbetareader.converter.Converter;
import cn.j1angvei.cnbetareader.converter.HeadlineConverter;
import cn.j1angvei.cnbetareader.converter.ReviewConverter;
import cn.j1angvei.cnbetareader.data.DataRepository;
import cn.j1angvei.cnbetareader.data.local.LocalDataSource;
import cn.j1angvei.cnbetareader.data.remote.CnbetaApi;
import cn.j1angvei.cnbetareader.data.remote.RemoteDataSource;
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
    @Named("l_article")
    LocalDataSource<Article> provideArticleLocalDataSource() {
        return new LocalDataSource<>();
    }

    @Provides
    @Singleton
    @Named("l_review")
    LocalDataSource<Review> provideReviewLocalDataSource() {
        return new LocalDataSource<>();
    }

    @Provides
    @Singleton
    @Named("l_headline")
    LocalDataSource<Headline> provideHeadlineLocalDataSource() {
        return new LocalDataSource<>();
    }

    @Provides
    @Singleton
    @Named("r_article")
    RemoteDataSource<Article> provideArticleRemoteDataSource(CnbetaApi api, @Named("c_article") Converter<Article> converter) {
        return new RemoteDataSource<>(api, converter);
    }

    @Provides
    @Singleton
    @Named("r_review")
    RemoteDataSource<Review> provideReviewRemoteDataSource(CnbetaApi api, @Named("c_review") Converter<Review> converter) {
        return new RemoteDataSource<>(api, converter);
    }

    @Provides
    @Singleton
    @Named("r_headline")
    RemoteDataSource<Headline> provideHeadlineRemoteDataSource(CnbetaApi api, @Named("c_headline") Converter<Headline> converter) {
        return new RemoteDataSource<>(api, converter);
    }

    @Provides
    @Singleton
    @Named("d_article")
    DataRepository<Article> provideArticleRepository(@Named("l_article") LocalDataSource<Article> local, @Named("r_article") RemoteDataSource<Article> remote) {
        return new DataRepository<>(local, remote);
    }

    @Provides
    @Singleton
    @Named("d_review")
    DataRepository<Review> provideReviewRepository(@Named("l_review") LocalDataSource<Review> local, @Named("r_review") RemoteDataSource<Review> remote) {
        return new DataRepository<>(local, remote);
    }

    @Provides
    @Singleton
    @Named("d_headline")
    DataRepository<Headline> provideHeadlineRepository(@Named("l_headline") LocalDataSource<Headline> local, @Named("r_headline") RemoteDataSource<Headline> remote) {
        return new DataRepository<>(local, remote);
    }
}
