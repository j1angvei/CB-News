package cn.j1angvei.cbnews.di.module.sub;

import android.support.v4.app.Fragment;

import javax.inject.Named;

import cn.j1angvei.cbnews.adapter.ArticlesRvAdapter;
import cn.j1angvei.cbnews.adapter.NewsAdapter;
import cn.j1angvei.cbnews.bean.Article;
import cn.j1angvei.cbnews.data.repository.NewsRepository;
import cn.j1angvei.cbnews.di.scope.PerFragment;
import cn.j1angvei.cbnews.presenter.NewsPresenter;
import dagger.Module;
import dagger.Provides;

import static cn.j1angvei.cbnews.bean.News.Type.ARTICLE;

/**
 * Created by Wayne on 2016/7/22.
 * article specific for {@link cn.j1angvei.cbnews.fragment.ArticlesFragment}
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
    NewsPresenter<Article> articlesPresenter(@Named(ARTICLE) NewsRepository<Article> repository) {
        return new NewsPresenter<>(repository);
    }
}
