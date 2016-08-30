package cn.j1angvei.cnbetareader.di.module.sub;

import android.support.v4.app.Fragment;

import javax.inject.Named;

import cn.j1angvei.cnbetareader.adapter.ArticlesRvAdapter;
import cn.j1angvei.cnbetareader.adapter.NewsAdapter;
import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.data.repository.NewsRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.presenter.NewsPresenter;
import cn.j1angvei.cnbetareader.util.ApiUtil;
import dagger.Module;
import dagger.Provides;

import static cn.j1angvei.cnbetareader.bean.News.Type.ARTICLE;

/**
 * Created by Wayne on 2016/7/22.
 * article specific for {@link cn.j1angvei.cnbetareader.fragment.ArticlesFragment}
 */
@Module
public class ArticleModule {

    @Provides
    @PerFragment
    NewsAdapter<Article, ArticlesRvAdapter.ViewHolder> provideArticlesRvAdapter(Fragment fragment) {
        return new ArticlesRvAdapter(fragment);
    }

    @Provides
    @PerFragment
    NewsPresenter<Article> articlesPresenter(@Named(ARTICLE) NewsRepository<Article> repository, ApiUtil apiUtil) {
        return new NewsPresenter<>(repository, apiUtil);
    }
}
