package cn.j1angvei.cbnews.newslist.headline;

import cn.j1angvei.cbnews.newslist.NewsFragment;
import cn.j1angvei.cbnews.bean.Headline;
import cn.j1angvei.cbnews.di.component.ActivityComponent;
import cn.j1angvei.cbnews.di.module.FragmentModule;
import cn.j1angvei.cbnews.di.module.sub.HeadlineModule;

/**
 * Created by Wayne on 2016/7/5.
 * fragment display Headline news
 */
public class HeadlineFragment extends NewsFragment<Headline, HeadlineRvAdapter.ViewHolder> {

    public static HeadlineFragment newInstance(String type) {
        HeadlineFragment fragment = new HeadlineFragment();
        fragment.setBundle(type);
        return fragment;
    }

    @Override
    protected void inject(ActivityComponent component) {
        component.headlineComponent(new HeadlineModule(), new FragmentModule(this)).inject(this);
    }

}
