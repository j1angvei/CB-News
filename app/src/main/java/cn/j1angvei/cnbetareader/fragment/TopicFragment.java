package cn.j1angvei.cnbetareader.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
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

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.activity.BaseActivity;
import cn.j1angvei.cnbetareader.adapter.ExploreAdapter;
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
    ExploreAdapter mAdapter;
    @Inject
    TopicPresenter mPresenter;
    CoordinatorLayout mCoordinatorLayout;
    private Spinner mSpinner;
    private FloatingActionButton mFab;
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
        //set context action mode
        mGridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        mGridView.setMultiChoiceModeListener(new TopicCABListener());
        //make gridView response to coordinatorLayout scroll behavior
        ViewCompat.setNestedScrollingEnabled(mGridView, true);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //fab
        mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        //coordinatorLayout
        mCoordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinator_layout);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.setView(this);
//        onRefresh();
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
    public void onAddSuccess() {
        MessageUtil.snack(mCoordinatorLayout, "Add success!");
    }

    @Override
    public void onAddFail() {
        MessageUtil.snack(mCoordinatorLayout, "Added already!");
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

    private class TopicCABListener implements GridView.MultiChoiceModeListener {
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position,
                                              long id, boolean checked) {
            // Here you can do something when items are selected/de-selected,
            // such as update the title in the CAB
            int count = mGridView.getCheckedItemCount();
            if (count == 1) {
                mode.setTitle(count + " topic selected");
            } else if (count > 1) {
                mode.setTitle(count + " topics selected");
            }
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            // Respond to clicks on the actions in the CAB
            switch (item.getItemId()) {
                case R.id.menu_context_explore_select_all:
                    for (int i = 0; i < mGridView.getCount(); i++) {
                        mGridView.setItemChecked(i, true);
                    }
                    return true;
                case R.id.menu_context_explore_add:
                    Set<String> ids = new HashSet<>();
                    SparseBooleanArray checked = mGridView.getCheckedItemPositions();
                    for (int i = 0; i < mGridView.getCount(); i++) {
                        if (checked.get(i)) {
                            Topic topic = mAdapter.getItem(i);
                            if (topic != null) {
                                ids.add(topic.getId());
                            }
                        }
                    }
                    mPresenter.saveMyTopicIds(ids);
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate the menu for the CAB
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_context_explore, menu);
            mode.setTitle("Select topics");
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            // Here you can make any necessary updates to the activity when
            // the CAB is removed. By default, selected items are deselected/unchecked.
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            // Here you can perform updates to the CAB due to
            // an invalidate() request
            return true;
        }
    }
}
