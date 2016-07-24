package cn.j1angvei.cnbetareader.di.module.sub;

import android.app.Activity;

import cn.j1angvei.cnbetareader.adapter.ExploreRvAdapter;
import cn.j1angvei.cnbetareader.data.repository.ExploreRepository;
import cn.j1angvei.cnbetareader.di.module.FragmentModule;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.presenter.ExplorePresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Wayne on 2016/7/15.
 */
@Module(includes = FragmentModule.class)
public class ExploreModule {
    @Provides
    @PerFragment
    ExploreRvAdapter provideExploreRvAdapter(Activity activity) {
        return new ExploreRvAdapter(activity);
    }

    @Provides
    @PerFragment
    ExplorePresenter provideExplorePresenter(ExploreRepository repository) {
        return new ExplorePresenter(repository);
    }

}
