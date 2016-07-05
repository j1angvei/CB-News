package cn.j1angvei.cnbetareader.newslist.hotcomments;

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
import cn.j1angvei.cnbetareader.bean.Review;
import cn.j1angvei.cnbetareader.di.module.FragmentModule;

/**
 * Created by Wayne on 2016/7/5.
 */
public class ReviewFragment extends BaseFragment implements ReviewContract.View {
    private static final String REVIEW_TYPE = "ReviewFragment.review_type";
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    ReviewRvAdapter mAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    ReviewPresenter mPresenter;

    private String mReviewType;

    public static ReviewFragment newInstance(String reviewType) {
        ReviewFragment fragment = new ReviewFragment();
        Bundle args = new Bundle();
        args.putString(REVIEW_TYPE, reviewType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReviewType = getArguments().getString(REVIEW_TYPE);
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
                mPresenter.retrieveLatestReviews();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.setView(this);
        mPresenter.retrieveLatestReviews();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecyclerView.setAdapter(null);
        mRecyclerView.setLayoutManager(null);
    }

    @Override
    public void addReviews(Review review) {
        mAdapter.add(review);
    }

    @Override
    public void clearReviews() {
        mAdapter.clear();
    }

    @Override
    public String getSourceType() {
        return mReviewType;
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
