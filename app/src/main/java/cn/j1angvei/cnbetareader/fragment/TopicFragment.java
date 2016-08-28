package cn.j1angvei.cnbetareader.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.activity.BaseActivity;
import cn.j1angvei.cnbetareader.adapter.TopicAdapter;
import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.contract.TopicContract;
import cn.j1angvei.cnbetareader.di.component.ActivityComponent;
import cn.j1angvei.cnbetareader.di.module.FragmentModule;
import cn.j1angvei.cnbetareader.presenter.TopicPresenter;
import cn.j1angvei.cnbetareader.util.MessageUtil;

/**
 * Created by Wayne on 2016/7/13.
 */
public class TopicFragment extends BaseFragment implements TopicContract.View, SwipeRefreshLayout.OnRefreshListener {
    private static final String PAGE = "TopicFragment.page";
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.grid_view)
    GridView mGridView;
    @Inject
    TopicAdapter mAdapter;
    @Inject
    TopicPresenter mPresenter;
    private Spinner mSpinner;
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
        setHasOptionsMenu(true);
        mPage = getArguments().getInt(PAGE);
        inject(((BaseActivity) getActivity()).getActivityComponent());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_explore, menu);
        MenuItem item = menu.findItem(R.id.menu_spinner);
        mSpinner = (Spinner) item.getActionView();
        setupViewInMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_spinner:
                MessageUtil.toast("spinner", getActivity());
                break;
            default:
                break;
        }
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        ButterKnife.bind(this, view);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MessageUtil.toast("test " + i, getContext());
            }
        });
        //make gridView response to coordinatorLayout scroll behavior
        ViewCompat.setNestedScrollingEnabled(mGridView, true);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //fab
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.setView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
    public void onRefresh() {
        clearTopics();
        mPresenter.retrieveTopics(mPage);
    }

    private void setupViewInMenu() {
        //spinner
        ArrayAdapter<Character> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item);
        for (int i = 0; i < 26; i++) {
            adapter.add((char) ('A' + i));
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setSelection(mPage - 1);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mPage = i + 1;
                onRefresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                MessageUtil.toast("nothing  selected", getActivity());
            }
        });
    }

}
