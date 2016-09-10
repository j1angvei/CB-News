package cn.j1angvei.cbnews.di.component;

import android.app.Application;

import javax.inject.Singleton;

import cn.j1angvei.cbnews.CBApplication;
import cn.j1angvei.cbnews.bean.Article;
import cn.j1angvei.cbnews.bean.Bookmark;
import cn.j1angvei.cbnews.bean.Comments;
import cn.j1angvei.cbnews.bean.Content;
import cn.j1angvei.cbnews.bean.Headline;
import cn.j1angvei.cbnews.bean.Review;
import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.data.remote.api.CBApiWrapper;
import cn.j1angvei.cbnews.data.repository.Repository;
import cn.j1angvei.cbnews.di.module.ApplicationModule;
import cn.j1angvei.cbnews.di.module.RepositoryModule;
import cn.j1angvei.cbnews.di.qualifier.QArticle;
import cn.j1angvei.cbnews.di.qualifier.QBookmark;
import cn.j1angvei.cbnews.di.qualifier.QCmt;
import cn.j1angvei.cbnews.di.qualifier.QContent;
import cn.j1angvei.cbnews.di.qualifier.QHeadline;
import cn.j1angvei.cbnews.di.qualifier.QReview;
import cn.j1angvei.cbnews.di.qualifier.QTopic;
import cn.j1angvei.cbnews.util.AppUtil;
import cn.j1angvei.cbnews.util.NetworkUtil;
import cn.j1angvei.cbnews.util.PrefsUtil;
import dagger.Component;

/**
 * Created by Wayne on 2016/6/15.
 * component specific for {@link cn.j1angvei.cbnews.CBApplication}
 */
@Singleton
@Component(modules = {ApplicationModule.class, RepositoryModule.class})
public interface ApplicationComponent {

    void inject(CBApplication application);

    //expose to sub activity graph
    Application application();

    @QArticle
    Repository<Article> articleRepository();

    @QReview
    Repository<Review> reviewRepository();

    @QHeadline
    Repository<Headline> headlineRepository();

    @QBookmark
    Repository<Bookmark> bookmarkRepository();

    @QTopic
    Repository<Topic> exploreRepository();

    @QContent
    Repository<Content> contentRepository();

    @QCmt
    Repository<Comments> commentsRepository();

    PrefsUtil prefUtil();

    CBApiWrapper apiHelper();

    NetworkUtil networkUtil();

    AppUtil appUtil();
}
