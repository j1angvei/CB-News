package cn.j1angvei.cnbetareader.fragment;

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
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.activity.BaseActivity;
import cn.j1angvei.cnbetareader.adapter.CommentsRvAdapter;
import cn.j1angvei.cnbetareader.bean.Comments;
import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.contract.ShowCmtContract;
import cn.j1angvei.cnbetareader.di.component.ActivityComponent;
import cn.j1angvei.cnbetareader.di.module.FragmentModule;
import cn.j1angvei.cnbetareader.presenter.ShowCmtPresenter;
import cn.j1angvei.cnbetareader.util.MessageUtil;

/**
 * Created by Wayne on 2016/7/28.
 */
public class ShowCmtFragment extends BaseFragment implements ShowCmtContract.View {
    private static final String NEWS_CONTENT = "ShowCmtFragment.news_content";
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
    private Content mContent;

    public static ShowCmtFragment newInstance(Content content) {
        ShowCmtFragment fragment = new ShowCmtFragment();
        Bundle args = new Bundle();
        args.putParcelable(NEWS_CONTENT, content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void inject(ActivityComponent component) {
        component.fragmentComponent(new FragmentModule()).inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        mContent = getArguments().getParcelable(NEWS_CONTENT);
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
    }

    @Override
    public void refreshComments() {
        mPresenter.retrieveComments(mContent.getSid(), mContent.getSn());
    }

    @Override
    public void renderComments(Comments comments) {
        mAdapter.add(comments);
    }

    @Override
    public void toJudgeComment(String action, String tid) {
        mPresenter.judgeComment(action, mContent.getSid(), tid);
    }

    @Override
    public void onJudgeSuccess() {
        MessageUtil.snack(mCoordinatorLayout, "judge success");
    }

    @Override
    public void onJudgeFail() {
        MessageUtil.snack(mCoordinatorLayout, "judge fail");

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
    public Context getViewContext() {
        return getActivity();
    }
}
