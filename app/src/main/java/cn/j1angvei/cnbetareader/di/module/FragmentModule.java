package cn.j1angvei.cnbetareader.di.module;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import javax.inject.Named;

import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Wayne on 2016/7/23.
 * module for normal {@link Fragment}
 */
@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    @PerFragment
    Fragment provideFragment() {
        return mFragment;
    }

    @Provides
    @PerFragment
    LinearLayoutManager provideLinearLayoutManager(Fragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }

    @Provides
    @PerFragment
    GridLayoutManager provideGridLayoutManager(Fragment fragment) {
        return new GridLayoutManager(fragment.getContext(), 4);
    }

    @Provides
    @PerFragment
    @Named("fm_c")
    FragmentManager provideFragmentManager(Fragment fragment) {
        return fragment.getChildFragmentManager();
    }
}
