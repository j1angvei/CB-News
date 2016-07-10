package cn.j1angvei.cnbetareader.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;


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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.setView(this);
        mPresenter.retrieveItem(mType, mPage++);
    }

    @Override
    public void renderItem(Article item) {
        mAdapter.add(item);
    }

    @Override
    public void clearItems() {
        mAdapter.clear();
    }

    @Override
    public void onRefresh() {
        mPresenter.retrieveItem(mType, mPage++);
    }
}
