package cn.j1angvei.cnbetareader.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.activity.BaseActivity;
import cn.j1angvei.cnbetareader.adapter.ExploreRvAdapter;
import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.di.component.ActivityComponent;
import cn.j1angvei.cnbetareader.di.module.sub.ExploreModule;
import cn.j1angvei.cnbetareader.presenter.ExplorePresenter;
import cn.j1angvei.cnbetareader.util.ToastUtil;
import cn.j1angvei.cnbetareader.view.ExploreView;

/**
 * Created by Wayne on 2016/7/13.
 */
public class ExploreFragment extends BaseFragment implements ExploreView, SwipeRefreshLayout.OnRefreshListener {
    private static final String PAGE = "ExploreFragment.page";
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Inject
    GridLayoutManager mGridLayoutManager;
    @Inject
    ExploreRvAdapter mAdapter;
    @Inject
    ExplorePresenter mPresenter;

    private int mPage;

    Spinner mSpinner;

    public static ExploreFragment newInstance(int page) {
        ExploreFragment fragment = new ExploreFragment();
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
                ToastUtil.showShortToast("spiiner", getActivity());
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
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.setView(this);
        onRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecyclerView.setAdapter(null);
        mRecyclerView.setLayoutManager(null);
    }

    @Override
    protected void inject(ActivityComponent component) {
        component.exploreComponent(new ExploreModule()).inject(this);
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
    public void renderItem(Topic item) {
        mAdapter.add(item);
    }

    @Override
    public void clearItems() {
        mAdapter.clear();
    }

    @Override
    public void onRefresh() {
        clearItems();
        mPresenter.retrieveTopics(mPage);
    }

    private void setupViewInMenu() {
        //spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.alphabet_caps, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mPage = i + 1;
                onRefresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                ToastUtil.showShortToast("nothing  selected", getActivity());
            }
        });
    }
}
