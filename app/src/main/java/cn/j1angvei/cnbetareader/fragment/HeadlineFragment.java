package cn.j1angvei.cnbetareader.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import cn.j1angvei.cnbetareader.bean.Headline;
import cn.j1angvei.cnbetareader.adapter.HeadlineRvAdapter;
import cn.j1angvei.cnbetareader.di.component.ActivityComponent;
import cn.j1angvei.cnbetareader.di.module.HeadlinesModule;

/**
 * Created by Wayne on 2016/7/5.
 */
public class HeadlineFragment extends SwipeFragment<Headline, HeadlineRvAdapter.ViewHolder> {

    public static HeadlineFragment newInstance(String type) {
        HeadlineFragment fragment = new HeadlineFragment();
        fragment.setBundle(type);
        return fragment;
    }

    @Override
    protected void inject(ActivityComponent component) {
        component.headlinesComponent(new HeadlinesModule()).inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.setView(this);
        mPresenter.retrieveItem(mType, mPage++);
    }

    @Override
    public void renderItem(Headline item) {
        mAdapter.add(item);
    }

    @Override
    public void clearItems() {
        mAdapter.clear();
    }

    @Override
    public void onRefresh() {

    }
}
