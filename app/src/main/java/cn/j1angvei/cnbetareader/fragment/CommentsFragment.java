package cn.j1angvei.cnbetareader.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.activity.BaseActivity;
import cn.j1angvei.cnbetareader.adapter.CommentsRvAdapter;
import cn.j1angvei.cnbetareader.bean.CommentItem;
import cn.j1angvei.cnbetareader.bean.Comments;
import cn.j1angvei.cnbetareader.di.component.ActivityComponent;
import cn.j1angvei.cnbetareader.di.module.FragmentModule;
import cn.j1angvei.cnbetareader.presenter.CommentsPresenter;

/**
 * Created by Wayne on 2016/7/28.
 */
public class CommentsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String COMMENTS = "CommentsFragment.comments";
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    CommentsRvAdapter mAdapter;
    @Inject
    CommentsPresenter mPresenter;

    private Comments mComments;

    public static CommentsFragment newInstance(Comments comments) {
        CommentsFragment fragment = new CommentsFragment();
        Bundle args = new Bundle();
        args.putParcelable(COMMENTS, comments);
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
        mComments = getArguments().getParcelable(COMMENTS);
        inject(((BaseActivity) getActivity()).getActivityComponent());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_comments, container, false);
        ButterKnife.bind(this, view);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        renderItem(mComments);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecyclerView.setAdapter(null);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
    }

    public void renderItem(Comments item) {
        List<String> all = item.getAllIds();
        List<CommentItem> items = new ArrayList<>();
        for (String id : all) {
            CommentItem commentItem = item.getCommentMap().get(id);
            items.add(commentItem);
        }
        mAdapter.add(items);
    }

    @Override
    public void onRefresh() {
        mAdapter.clear();
    }

}
