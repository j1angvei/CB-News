package cn.j1angvei.cnbetareader.di.module;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Wayne on 2016/6/16.
 */
@Module
public class FragmentModule {

    @Provides
    @PerFragment
    LinearLayoutManager provideLinearLayoutManager(Activity activity) {
        return new LinearLayoutManager(activity);
    }

    @Provides
    @PerFragment
    GridLayoutManager provideGridLayoutManager(Activity activity) {
        return new GridLayoutManager(activity, 4);
    }

}
