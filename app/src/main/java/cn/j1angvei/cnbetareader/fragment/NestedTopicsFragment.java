package cn.j1angvei.cnbetareader.fragment;

import android.view.View;

import cn.j1angvei.cnbetareader.adapter.ArticlesRvAdapter;
import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.di.component.ActivityComponent;
import cn.j1angvei.cnbetareader.di.module.MyTopicsModule;

/**
 * Created by Wayne on 2016/7/9.
 */
public class NestedTopicsFragment extends SwipeFragment<Article, ArticlesRvAdapter.ViewHolder> {

    public static NestedTopicsFragment newInstance(String topicId) {
        NestedTopicsFragment fragment = new NestedTopicsFragment();
        fragment.setBundle(topicId);
        return fragment;
    }

    @Override
    protected void inject(ActivityComponent component) {
        component.topicsComponent(new MyTopicsModule()).inject(this);
    }

    @Override
    public void onClick(View view) {

    }
}
