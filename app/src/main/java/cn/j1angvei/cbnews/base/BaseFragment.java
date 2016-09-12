package cn.j1angvei.cbnews.base;

import android.support.v4.app.Fragment;

import cn.j1angvei.cbnews.di.component.ActivityComponent;

/**
 * Created by Wayne on 2016/6/28.
 */
public abstract class BaseFragment extends Fragment {

    protected abstract void inject(ActivityComponent component);
}
