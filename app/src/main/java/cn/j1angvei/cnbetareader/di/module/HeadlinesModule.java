package cn.j1angvei.cnbetareader.di.module;

import android.app.Activity;

import cn.j1angvei.cnbetareader.adapter.HeadlineRvAdapter;
import cn.j1angvei.cnbetareader.adapter.SwipeAdapter;
import cn.j1angvei.cnbetareader.bean.Headline;
import cn.j1angvei.cnbetareader.data.DataRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.presenter.HeadlinePresenter;
import cn.j1angvei.cnbetareader.presenter.SwipePresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Wayne on 2016/7/10.
 */
@Module(includes = FragmentModule.class)
public class HeadlinesModule {
    @Provides
    @PerFragment
    SwipeAdapter<Headline, HeadlineRvAdapter.ViewHolder> provideHeadlineRvAdapter(Activity activity) {
        return new HeadlineRvAdapter(activity);
    }

    @Provides
    @PerFragment
    SwipePresenter<Headline> headlinesPresenter(DataRepository repository) {
        return new HeadlinePresenter(repository);
    }
}
