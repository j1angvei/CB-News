package cn.j1angvei.cnbetareader.di.module.sub;

import android.app.Activity;

import cn.j1angvei.cnbetareader.adapter.ArticlesRvAdapter;
import cn.j1angvei.cnbetareader.data.repository.MyTopicsRepository;
import cn.j1angvei.cnbetareader.di.module.FragmentModule;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.presenter.NestedTopicsPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Wayne on 2016/7/10.
 */
@Module(includes = FragmentModule.class)
public class NestedTopicsModule {
    @Provides
    @PerFragment
    ArticlesRvAdapter provideTopicsRvAdapter(Activity activity) {
        return new ArticlesRvAdapter(activity);
    }

    @Provides
    @PerFragment
    NestedTopicsPresenter topicsPresenter(MyTopicsRepository repository) {
        return new NestedTopicsPresenter(repository);
    }
}
