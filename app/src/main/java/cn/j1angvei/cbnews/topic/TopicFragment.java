package cn.j1angvei.cbnews.topic;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import cn.j1angvei.cbnews.base.BaseActivity;
import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.di.component.ActivityComponent;
import cn.j1angvei.cbnews.di.module.FragmentModule;
import cn.j1angvei.cbnews.base.BaseFragment;

/**
 * Created by Wayne on 2016/7/13.
 */
public class TopicFragment extends BaseFragment implements TopicContract.View {
    private static final String TAG = "TopicFragment";
    private static final String PAGE = "TopicFragment.page";
    private static final String DIALOG_PICK_LETTER = "TopicFragment.dialog_pick_letter";
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Inject
    GridLayoutManager mGridLayoutManager;
    @Inject
    TopicRvAdapter mAdapter;
    @Inject
    TopicPresenter mPresenter;
    FloatingActionButton mFab;
    private int mPage;

    public static TopicFragment newInstance(int page) {
        TopicFragment fragment = new TopicFragment();
        Bundle args = new Bundle();
        args.putInt(PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        mPage = getArguments().getInt(PAGE);
        inject(((BaseActivity) getActivity()).getActivityComponent());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_all_topics, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_choose_letter:
                showLetterPickDialog();
            default:
                break;
        }
        return true;
    }

    private void showLetterPickDialog() {
        PickLetterDialog dialog = PickLetterDialog.newInstance(mPage);
        dialog.setTargetFragment(this, 0);
        dialog.show(getChildFragmentManager(), DIALOG_PICK_LETTER);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_topics, container, false);
        ButterKnife.bind(this, view);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearTopics();
                mPresenter.retrieveTopics(-1);
            }
        });
        mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mFab.setImageResource(R.drawable.ic_choose_letter_white);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLetterPickDialog();
            }
        });
        mFab.show();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.setView(this);
        mPresenter.retrieveTopics(mPage);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mFab.hide();
        mFab.setOnClickListener(null);
        mRecyclerView.setAdapter(null);
        mRecyclerView.setLayoutManager(null);
    }

    @Override
    protected void inject(ActivityComponent component) {
        component.fragmentComponent(new FragmentModule(this)).inject(this);
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
        return getContext();
    }

    @Override
    public void renderTopic(Topic item) {
        mAdapter.add(item);
    }

    @Override
    public void clearTopics() {
        mAdapter.clear();
    }

    @Override
    public void onLetterChosen(int page) {
        if (page == mPage) {
            Log.d(TAG, "onLetterChosen: same page, nothing changed");
            return;
        }
        mPage = page;
        clearTopics();
        mPresenter.retrieveTopics(mPage);
    }


}
