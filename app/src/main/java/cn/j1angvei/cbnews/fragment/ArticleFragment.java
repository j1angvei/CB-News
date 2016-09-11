package cn.j1angvei.cbnews.fragment;

import cn.j1angvei.cbnews.adapter.ArticlesRvAdapter;
import cn.j1angvei.cbnews.bean.Article;
import cn.j1angvei.cbnews.di.component.ActivityComponent;
import cn.j1angvei.cbnews.di.module.FragmentModule;
import cn.j1angvei.cbnews.di.module.sub.ArticleModule;

/**
 * Created by Wayne on 2016/7/4.
 */
public class ArticleFragment extends NewsFragment<Article, ArticlesRvAdapter.ViewHolder> {

    public static ArticleFragment newInstance(String type) {
        ArticleFragment fragment = new ArticleFragment();
        fragment.setBundle(type);
        return fragment;
    }

    @Override
    protected void inject(ActivityComponent component) {
        component.articleComponent(new ArticleModule(),new FragmentModule(this)).inject(this);
    }
}
