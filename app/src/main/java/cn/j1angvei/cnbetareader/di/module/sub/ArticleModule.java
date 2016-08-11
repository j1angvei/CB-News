package cn.j1angvei.cnbetareader.di.module.sub;

import android.app.Activity;

import javax.inject.Named;

import cn.j1angvei.cnbetareader.adapter.ArticlesRvAdapter;
import cn.j1angvei.cnbetareader.adapter.NewsAdapter;
import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.data.repository.NewsRepository;
import cn.j1angvei.cnbetareader.di.module.FragmentModule;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.presenter.NewsPresenter;
import cn.j1angvei.cnbetareader.util.ApiUtil;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Wayne on 2016/7/22.
 */
@Module(includes = FragmentModule.class)
public class ArticleModule {

    @Provides
    @PerFragment
    NewsAdapter<Article, ArticlesRvAdapter.ViewHolder> provideArticlesRvAdapter(Activity activity) {
        return new ArticlesRvAdapter(activity);
    }

    @Provides
    @PerFragment
    NewsPresenter<Article> articlesPresenter(@Named("d_article") NewsRepository<Article> repository, ApiUtil apiUtil) {
        return new NewsPresenter<>(repository, apiUtil);
    }

}
