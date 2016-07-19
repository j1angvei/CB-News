package cn.j1angvei.cnbetareader.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.activity.BaseActivity;
import cn.j1angvei.cnbetareader.adapter.SwipeAdapter;
import cn.j1angvei.cnbetareader.di.component.ActivityComponent;
import cn.j1angvei.cnbetareader.fragment.dialog.SeekPageDialog;
import cn.j1angvei.cnbetareader.presenter.SwipePresenter;
import cn.j1angvei.cnbetareader.view.SwipeView;

/**
 * Created by Wayne on 2016/7/9.
 */
public abstract class SwipeFragment<T, VH extends RecyclerView.ViewHolder> extends BaseFragment implements SwipeView<T>, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    static final String NEWS_TYPE = "SwipeFragment.news_type";
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    GridLayoutManager mGridLayoutManager;
    @Inject
    SwipeAdapter<T, VH> mAdapter;
    @Inject
    SwipePresenter<T> mPresenter;

    FloatingActionButton mFab;

    String mType;
    int mPage = 1;

    void setBundle(String type) {
        Bundle args = new Bundle();
        args.putString(NEWS_TYPE, type);
        setArguments(args);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getString(NEWS_TYPE);
        inject(((BaseActivity) getActivity()).getActivityComponent());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.include_swipe_recycler_view, container, false);
        mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        ButterKnife.bind(this, view);
        mRecyclerView.setAdapter(mAdapter);
        setLayoutManager();
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mSwipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    public void setLayoutManager() {
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFab.setOnClickListener(this);
        mPresenter.setView(this);
        clearItems();
        retrieveItem();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecyclerView.setAdapter(null);
        mRecyclerView.setLayoutManager(null);
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
    public void renderItem(T item) {
        mAdapter.add(item);
    }

    @Override
    public void clearItems() {
        mPage = 1;
        mAdapter.clear();
    }

    @Override
    public void onRefresh() {
        clearItems();
        retrieveItem();
    }

    protected void retrieveItem() {
        mPresenter.retrieveItem(mType, mPage++);
    }

    protected abstract void inject(ActivityComponent component);

}
