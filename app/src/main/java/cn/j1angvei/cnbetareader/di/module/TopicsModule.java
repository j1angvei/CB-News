package cn.j1angvei.cnbetareader.di.module;

import android.app.Activity;

import cn.j1angvei.cnbetareader.adapter.ArticlesRvAdapter;
import cn.j1angvei.cnbetareader.adapter.SwipeAdapter;
import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.data.DataRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.presenter.SwipePresenter;
import cn.j1angvei.cnbetareader.presenter.TopicsPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Wayne on 2016/7/10.
 */
@Module(includes = FragmentModule.class)
public class TopicsModule {
    @Provides
    @PerFragment
    SwipeAdapter<Article, ArticlesRvAdapter.ViewHolder> provideTopicsRvAdapter(Activity activity) {
        return new ArticlesRvAdapter(activity);
    }

    @Provides
    @PerFragment
    SwipePresenter<Article> topicsPresenter(DataRepository repository) {
        return new TopicsPresenter(repository);
    }
}
