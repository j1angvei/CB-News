package cn.j1angvei.cnbetareader.di.module.sub;

import android.app.Activity;

import javax.inject.Named;

import cn.j1angvei.cnbetareader.adapter.HeadlineRvAdapter;
import cn.j1angvei.cnbetareader.adapter.SwipeAdapter;
import cn.j1angvei.cnbetareader.bean.Headline;
import cn.j1angvei.cnbetareader.data.DataRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.presenter.NewsPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Wayne on 2016/7/22.
 */
@Module
public class HeadlineModule {
    @Provides
    @PerFragment
    SwipeAdapter<Headline, HeadlineRvAdapter.ViewHolder> provideHeadlineRvAdapter(Activity activity) {
        return new HeadlineRvAdapter(activity);
    }

    @Provides
    @PerFragment
    NewsPresenter<Headline> headlinesPresenter(@Named("d_headline") DataRepository<Headline> repository) {
        return new NewsPresenter<>(repository);
    }
}
