package cn.j1angvei.cnbetareader.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.activity.BaseActivity;
import cn.j1angvei.cnbetareader.adapter.AllTopicsRvAdapter;
import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.contract.AllTopicsContract;
import cn.j1angvei.cnbetareader.di.component.ActivityComponent;
import cn.j1angvei.cnbetareader.di.module.FragmentModule;
import cn.j1angvei.cnbetareader.dialog.PickLetterDialog;
import cn.j1angvei.cnbetareader.presenter.AllTopicsPresenter;

/**
 * Created by Wayne on 2016/7/13.
 */
public class AllTopicsFragment extends BaseFragment implements AllTopicsContract.View {
    private static final String TAG = "AllTopicsFragment";
    private static final String PAGE = "AllTopicsFragment.page";
    private static final String DIALOG_PICK_LETTER = "AllTopicsFragment.dialog_pick_letter";
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Inject
    GridLayoutManager mGridLayoutManager;
    @Inject
    AllTopicsRvAdapter mAdapter;
    @Inject
    AllTopicsPresenter mPresenter;
    private int mPage;

    public static AllTopicsFragment newInstance(int page) {
        AllTopicsFragment fragment = new AllTopicsFragment();
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
                mPresenter.retrieveTopics(mPage);
            }
        });
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
        mRecyclerView.setAdapter(null);
        mRecyclerView.setLayoutManager(null);
    }

    @Override
    protected void inject(ActivityComponent component) {
        component.fragmentComponent(new FragmentModule()).inject(this);
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
