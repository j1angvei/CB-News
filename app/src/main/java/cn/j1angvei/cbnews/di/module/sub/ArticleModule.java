package cn.j1angvei.cbnews.di.module.sub;

import android.support.v4.app.Fragment;

import cn.j1angvei.cbnews.adapter.ArticlesRvAdapter;
import cn.j1angvei.cbnews.adapter.NewsAdapter;
import cn.j1angvei.cbnews.bean.Article;
import cn.j1angvei.cbnews.data.repository.Repository;
import cn.j1angvei.cbnews.di.qualifier.QArticle;
import cn.j1angvei.cbnews.di.scope.PerFragment;
import cn.j1angvei.cbnews.presenter.NewsPresenter;
import dagger.Module;
import dagger.Provides;

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
    NewsPresenter<Article> articlesPresenter(@QArticle Repository<Article> repository) {
        return new NewsPresenter<>(repository);
    }
}
