package cn.j1angvei.cnbetareader.newslist.pastheadlines;

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
import cn.j1angvei.cnbetareader.bean.Headline;
import cn.j1angvei.cnbetareader.di.module.FragmentModule;

/**
 * Created by Wayne on 2016/7/5.
 */
public class HeadlineFragment extends BaseFragment implements HeadlinesContract.View {
    private static final String HEADLINE_TYPE = "ReviewFragment.headline_type";
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    HeadlineRvAdapter mAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    HeadlinePresenter mPresenter;

    private String mHeadlineType;

    public static HeadlineFragment newInstance(String headlineType) {
        HeadlineFragment fragment = new HeadlineFragment();
        Bundle args = new Bundle();
        args.putString(HEADLINE_TYPE, headlineType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHeadlineType = getArguments().getString(HEADLINE_TYPE);
        ((BaseActivity) getActivity()).getActivityComponent().fragmentComponent(new FragmentModule()).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        ButterKnife.bind(this, view);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.retrieveLatestHeadlines();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.setView(this);
        mPresenter.retrieveLatestHeadlines();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecyclerView.setAdapter(null);
        mRecyclerView.setLayoutManager(null);
    }

    @Override
    public void addHeadlines(Headline headline) {
        mAdapter.add(headline);
    }

    @Override
    public void clearHeadlines() {
        mAdapter.clear();
    }

    @Override
    public String getSourceType() {
        return mHeadlineType;
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
