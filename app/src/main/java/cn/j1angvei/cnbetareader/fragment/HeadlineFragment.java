package cn.j1angvei.cnbetareader.fragment;

import android.view.View;

import cn.j1angvei.cnbetareader.adapter.HeadlineRvAdapter;
import cn.j1angvei.cnbetareader.bean.Headline;
import cn.j1angvei.cnbetareader.di.component.ActivityComponent;
import cn.j1angvei.cnbetareader.di.module.sub.HeadlineModule;

/**
 * Created by Wayne on 2016/7/5.
 */
public class HeadlineFragment extends NewsFragment<Headline, HeadlineRvAdapter.ViewHolder> {

    public static HeadlineFragment newInstance(String type) {
        HeadlineFragment fragment = new HeadlineFragment();
        fragment.setBundle(type);
        return fragment;
    }

    @Override
    protected void inject(ActivityComponent component) {
        component.headlineComponent(new HeadlineModule()).inject(this);
    }

    @Override
    public void onClick(View view) {

    }
}
