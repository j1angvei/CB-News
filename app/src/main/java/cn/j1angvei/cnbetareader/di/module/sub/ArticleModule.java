package cn.j1angvei.cnbetareader.di.module.sub;

import android.app.Activity;

import javax.inject.Named;

import cn.j1angvei.cnbetareader.adapter.ArticlesRvAdapter;
import cn.j1angvei.cnbetareader.adapter.SwipeAdapter;
import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.data.repository.NewsRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.presenter.NewsPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Wayne on 2016/7/22.
 */
@Module
public class ArticleModule {

    @Provides
    @PerFragment
    SwipeAdapter<Article, ArticlesRvAdapter.ViewHolder> provideArticlesRvAdapter(Activity activity) {
        return new ArticlesRvAdapter(activity);
    }

    @Provides
    @PerFragment
    NewsPresenter<Article> articlesPresenter(@Named("d_article") NewsRepository<Article> repository) {
        return new NewsPresenter<>(repository);
    }

}
