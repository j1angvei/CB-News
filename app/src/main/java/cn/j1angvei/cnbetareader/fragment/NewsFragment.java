package cn.j1angvei.cnbetareader.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.activity.BaseActivity;
import cn.j1angvei.cnbetareader.adapter.NewsAdapter;
import cn.j1angvei.cnbetareader.bean.News;
import cn.j1angvei.cnbetareader.contract.NewsContract;
import cn.j1angvei.cnbetareader.listener.EndlessRecyclerViewScrollListener;
import cn.j1angvei.cnbetareader.presenter.NewsPresenter;

/**
 * Created by Wayne on 2016/7/9.
 * abstract class display news like Article Headline Review
 */
public abstract class NewsFragment<T extends News, VH extends RecyclerView.ViewHolder> extends BaseFragment implements NewsContract.View<T> {
    private static final String TAG = "NewsFragment";
    private static final String NEWS_TYPE = "NewsFragment.news_type";
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    NewsAdapter<T, VH> mAdapter;
    @Inject
    NewsPresenter<T> mPresenter;
    private FloatingActionButton mFab;
    private String mType;
    private int mPage = 1;
    private boolean isTopicNews;

    void setBundle(String type) {
        Bundle args = new Bundle();
        args.putString(NEWS_TYPE, type);
        setArguments(args);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        mType = getArguments().getString(NEWS_TYPE);
        isTopicNews = TextUtils.isDigitsOnly(mType);
        inject(((BaseActivity) getActivity()).getActivityComponent());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        ButterKnife.bind(this, view);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                mPresenter.retrieveNews(mType, mPage++);
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearNewses();
                mPresenter.retrieveNews(mType, mPage++);
            }
        });
        if (!isTopicNews) {
            mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
            mFab.setImageResource(R.drawable.ic_scroll_to_top);
            mFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRecyclerView.scrollToPosition(0);
                }
            });
            mFab.show();
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.setView(this);
        mPresenter.retrieveNews(mType, mPage++);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecyclerView.setAdapter(null);
        mRecyclerView.setLayoutManager(null);
        if (!isTopicNews) {
            mFab.hide();
            mFab.setOnClickListener(null);
        }

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
    public void renderNews(T item) {
        mAdapter.add(item);
    }

    @Override
    public void clearNewses() {
        mPage = 1;
        mAdapter.clear();
    }

}
