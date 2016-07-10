package cn.j1angvei.cnbetareader.fragment;

import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.di.component.ActivityComponent;
import cn.j1angvei.cnbetareader.di.module.ArticlesModule;
import cn.j1angvei.cnbetareader.adapter.ArticlesRvAdapter;

/**
 * Created by Wayne on 2016/7/4.
 */
public class ArticlesFragment extends SwipeFragment<Article, ArticlesRvAdapter.ViewHolder> {

    public static ArticlesFragment newInstance(String type) {
        ArticlesFragment fragment = new ArticlesFragment();
        fragment.setBundle(type);
        return fragment;
    }

    @Override
    protected void inject(ActivityComponent component) {
        component.articlesComponent(new ArticlesModule()).inject(this);
    }

}
