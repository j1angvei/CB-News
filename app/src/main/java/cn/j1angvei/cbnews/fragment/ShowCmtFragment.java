package cn.j1angvei.cbnews.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cbnews.R;
import cn.j1angvei.cbnews.activity.BaseActivity;
import cn.j1angvei.cbnews.activity.CommentsActivity;
import cn.j1angvei.cbnews.adapter.CommentsRvAdapter;
import cn.j1angvei.cbnews.bean.CommentItem;
import cn.j1angvei.cbnews.bean.Comments;
import cn.j1angvei.cbnews.contract.ShowCmtContract;
import cn.j1angvei.cbnews.di.component.ActivityComponent;
import cn.j1angvei.cbnews.di.module.FragmentModule;
import cn.j1angvei.cbnews.presenter.ShowCmtPresenter;
import cn.j1angvei.cbnews.util.MessageUtil;

/**
 * Created by Wayne on 2016/7/28.
 * show news comments
 */
public class ShowCmtFragment extends BaseFragment implements ShowCmtContract.View {
    private static final String NEWS_ID = "ShowCmtFragment.news_id";
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    CoordinatorLayout mCoordinatorLayout;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    ShowCmtPresenter mPresenter;
    @Inject
    CommentsRvAdapter mAdapter;
    private String mSid;

    public static ShowCmtFragment newInstance(String sid) {
        ShowCmtFragment fragment = new ShowCmtFragment();
        Bundle args = new Bundle();
        args.putString(NEWS_ID, sid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void inject(ActivityComponent component) {
        component.fragmentComponent(new FragmentModule(this)).inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        mSid = getArguments().getString(NEWS_ID);
        inject(((BaseActivity) getActivity()).getActivityComponent());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_comments, container, false);
        ButterKnife.bind(this, view);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshComments();
            }
        });
        mCoordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinator_layout);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.setView(this);
        refreshComments();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_comment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_comment_time_order:
                MessageUtil.toast("time order", getActivity());
                return true;
            case R.id.menu_comment_switch_comment:
                MessageUtil.toast("popular comment", getActivity());
                return true;
            case R.id.menu_comment_mini_card:
                MessageUtil.toast("mini card", getActivity());
                return true;
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecyclerView.setAdapter(null);
        mRecyclerView.setLayoutManager(null);
        mCoordinatorLayout = null;
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void refreshComments() {
        mPresenter.retrieveComments(mSid);
    }

    @Override
    public void renderComments(Comments comments) {
        if (comments.isOpen()) {
            mAdapter.clear();
            mAdapter.add(comments);
        } else {
            MessageUtil.toast(R.string.info_comment_closed, getActivity());
            getActivity().finish();
        }
    }

    @Override
    public void toJudgeComment(String action, String tid) {
        mPresenter.judgeComment(action, mSid, tid);
    }

    @Override
    public void onJudgeSuccess() {
        MessageUtil.snack(mCoordinatorLayout, R.string.info_cmt_success);
    }

    @Override
    public void onJudgeFail() {
        MessageUtil.snack(mCoordinatorLayout, R.string.info_cmt_fail);
    }

    @Override
    public void replyCmt(CommentItem item) {
        ((CommentsActivity) getActivity()).showPublishCmtDialog(false, item.getContent(), item.getSid(), item.getTid());
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
