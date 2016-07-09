package cn.j1angvei.cnbetareader.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.activity.BaseActivity;
import cn.j1angvei.cnbetareader.bean.Review;
import cn.j1angvei.cnbetareader.di.module.FragmentModule;
import cn.j1angvei.cnbetareader.presenter.ReviewPresenter;
import cn.j1angvei.cnbetareader.adapter.ReviewRvAdapter;

/**
 * Created by Wayne on 2016/7/5.
 */
public class ReviewFragment extends SwipeFragment<Review> {

    @Inject
    ReviewRvAdapter mAdapter;
    @Inject
    ReviewPresenter mPresenter;

    public static ReviewFragment newInstance(String type) {
        ReviewFragment fragment = new ReviewFragment();
        fragment.setBundle(type);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                mPage = 1;
                clearItems();
                mPresenter.retrieveItem(mType, mPage++);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.setView(this);
        mPresenter.retrieveItem(mType, mPage++);
    }


    @Override
    public void renderItem(Review item) {
        mAdapter.add(item);
    }

    @Override
    public void clearItems() {
        mAdapter.clear();
    }
}
