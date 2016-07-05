package cn.j1angvei.cnbetareader.newslist.latestnews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.base.BaseActivity;
import cn.j1angvei.cnbetareader.base.BaseFragment;
import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.di.module.FragmentModule;

/**
 * Created by Wayne on 2016/7/4.
 */
public class ArticlesFragment extends BaseFragment implements ArticlesContract.View {
    public static final String ARTICLE_TYPE = "ArticlesFragment.news_type";
    @BindView(R.id.srl_article)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.rv_article)
    RecyclerView mRecyclerView;

    @Inject
    ArticlesRvAdapter mAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    ArticlesPresenter mPresenter;

    private String mArticleType;

    public static ArticlesFragment newInstance(String newsType) {
        ArticlesFragment fragment = new ArticlesFragment();
        Bundle args = new Bundle();
        args.putString(ARTICLE_TYPE, newsType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArticleType = getArguments().getString(ARTICLE_TYPE);
        ((BaseActivity) getActivity()).getActivityComponent().fragmentComponent(new FragmentModule()).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_latest_news, container, false);
        ButterKnife.bind(this, view);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.retrieveLatestNews();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.setView(this);
        mPresenter.retrieveLatestNews();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecyclerView.setAdapter(null);
        mRecyclerView.setLayoutManager(null);
    }

    @Override
    public void addNews(Article article) {
        mAdapter.add(article);
    }

    @Override
    public void clearNews() {
        mAdapter.clear();
    }

    @Override
    public String getSourceType() {
        return mArticleType;
    }

    @Override
    public void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
