package cn.j1angvei.cnbetareader.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.activity.BaseActivity;
import cn.j1angvei.cnbetareader.adapter.ExploreRvAdapter;
import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.di.component.ActivityComponent;
import cn.j1angvei.cnbetareader.di.module.sub.ExploreModule;
import cn.j1angvei.cnbetareader.presenter.ExplorePresenter;
import cn.j1angvei.cnbetareader.view.ExploreView;

/**
 * Created by Wayne on 2016/7/13.
 */
public class ExploreFragment extends BaseFragment implements ExploreView, SwipeRefreshLayout.OnRefreshListener {
    private static final String PAGE = "ExploreFragment.page";
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Inject
    GridLayoutManager mGridLayoutManager;
    @Inject
    ExploreRvAdapter mAdapter;
    @Inject
    ExplorePresenter mPresenter;

    private int mPage;

    public static ExploreFragment newInstance(int page) {
        ExploreFragment fragment = new ExploreFragment();
        Bundle args = new Bundle();
        args.putInt(PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(PAGE);
        inject(((BaseActivity) getActivity()).getActivityComponent());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        ButterKnife.bind(this, view);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.setView(this);
        onRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecyclerView.setAdapter(null);
        mRecyclerView.setLayoutManager(null);
    }

    @Override
    protected void inject(ActivityComponent component) {
        component.exploreComponent(new ExploreModule()).inject(this);
    }


    @Override
    public void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void renderItem(Topic item) {
        mAdapter.add(item);
    }

    @Override
    public void clearItems() {
        mAdapter.clear();
    }

    @Override
    public void onRefresh() {
        clearItems();
        mPresenter.retrieveTopics(mPage);
    }
}
