package cn.j1angvei.cnbetareader.di.module;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import cn.j1angvei.cnbetareader.di.scope.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Wayne on 2016/6/15.
 * module for all {@link android.support.v7.app.AppCompatActivity}
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @PerActivity
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @PerActivity
    FragmentManager provideFragmentManager(Activity activity) {
        return ((FragmentActivity) activity).getSupportFragmentManager();
    }
}
