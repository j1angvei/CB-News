package cn.j1angvei.cnbetareader.di.module.sub;

import android.app.Activity;

import javax.inject.Named;

import cn.j1angvei.cnbetareader.adapter.HeadlineRvAdapter;
import cn.j1angvei.cnbetareader.adapter.NewsAdapter;
import cn.j1angvei.cnbetareader.bean.Headline;
import cn.j1angvei.cnbetareader.data.repository.NewsRepository;
import cn.j1angvei.cnbetareader.di.module.FragmentModule;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.presenter.NewsPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Wayne on 2016/7/22.
 */
@Module(includes = FragmentModule.class)
public class HeadlineModule {
    @Provides
    @PerFragment
    NewsAdapter<Headline, HeadlineRvAdapter.ViewHolder> provideHeadlineRvAdapter(Activity activity) {
        return new HeadlineRvAdapter(activity);
    }

    @Provides
    @PerFragment
    NewsPresenter<Headline> headlinesPresenter(@Named("d_headline") NewsRepository<Headline> repository) {
        return new NewsPresenter<>(repository);
    }
}