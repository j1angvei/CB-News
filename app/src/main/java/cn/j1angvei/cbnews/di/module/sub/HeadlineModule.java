package cn.j1angvei.cbnews.di.module.sub;

import android.support.v4.app.Fragment;

import javax.inject.Named;

import cn.j1angvei.cbnews.adapter.HeadlineRvAdapter;
import cn.j1angvei.cbnews.adapter.NewsAdapter;
import cn.j1angvei.cbnews.bean.Headline;
import cn.j1angvei.cbnews.data.repository.NewsRepository;
import cn.j1angvei.cbnews.di.scope.PerFragment;
import cn.j1angvei.cbnews.presenter.NewsPresenter;
import dagger.Module;
import dagger.Provides;

import static cn.j1angvei.cbnews.bean.News.Type.HEADLINE;


/**
 * Created by Wayne on 2016/7/22.
 */
@Module
public class HeadlineModule {
    @Provides
    @PerFragment
    NewsAdapter<Headline, HeadlineRvAdapter.ViewHolder> provideHeadlineRvAdapter(Fragment fragment) {
        return new HeadlineRvAdapter(fragment);
    }

    @Provides
    @PerFragment
    NewsPresenter<Headline> headlinesPresenter(@Named(HEADLINE) NewsRepository<Headline> repository) {
        return new NewsPresenter<>(repository);
    }
}
