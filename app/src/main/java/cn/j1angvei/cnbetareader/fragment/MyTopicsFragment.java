package cn.j1angvei.cnbetareader.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.activity.BaseActivity;
import cn.j1angvei.cnbetareader.adapter.MyTopicsPagerAdapter;
import cn.j1angvei.cnbetareader.bean.MyTopic;
import cn.j1angvei.cnbetareader.contract.MyTopicsContract;
import cn.j1angvei.cnbetareader.di.component.ActivityComponent;
import cn.j1angvei.cnbetareader.di.module.FragmentModule;
import cn.j1angvei.cnbetareader.dialog.AddMyTopicDialog;
import cn.j1angvei.cnbetareader.presenter.MyTopicsPresenter;

/**
 * Created by Wayne on 2016/7/6.
 * show user selected topic news
 */
public class MyTopicsFragment extends BaseFragment implements MyTopicsContract.View {
    private static final String TAG = "MyTopicsFragment";
    private static final String LATER_USE = "MyTopicsFragment.later_use";
    private static final String DIALOG_ADD_TOPIC = "add_topic";

    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.tv_my_topic_hint)
    TextView mHint;
    @Inject
    MyTopicsPresenter mPresenter;
    TabLayout mTabLayout;
    MyTopicsPagerAdapter mAdapter;
    CoordinatorLayout mCoordinatorLayout;
    FloatingActionButton mFab;

    public static MyTopicsFragment newInstance(String later) {
        //later is the value of Source.My_TOPICS
        MyTopicsFragment fragment = new MyTopicsFragment();
        Bundle args = new Bundle();
        args.putString(LATER_USE, later);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        inject(((BaseActivity) getActivity()).getActivityComponent());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_topics, container, false);
        ButterKnife.bind(this, view);
        mTabLayout = (TabLayout) getActivity().findViewById(R.id.tab_layout);
        mCoordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinator_layout);
        mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mFab.setImageResource(R.drawable.ic_add_white);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAllTopics();
            }
        });
        mFab.show();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_my_topics, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_topic:
                showAllTopics();
                break;
        }
        return true;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.setView(this);
        refreshMyTopics();
    }

    @Override
    public void onDestroyView() {
        mTabLayout.setVisibility(View.GONE);
        super.onDestroyView();
        mFab.hide();
        mFab.setOnClickListener(null);
    }

    @Override
    protected void inject(ActivityComponent component) {
        component.fragmentComponent(new FragmentModule(this)).inject(this);
    }

    @Override
    public void showLoading() {
        mHint.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mHint.setVisibility(View.GONE);
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void showAllTopics() {
        AddMyTopicDialog dialog = new AddMyTopicDialog();
        dialog.setTargetFragment(this, 0);
        dialog.show(getChildFragmentManager(), DIALOG_ADD_TOPIC);
    }

    @Override
    public void renderMyTopics(List<MyTopic> topics) {
        hideLoading();
        mAdapter = new MyTopicsPagerAdapter(getChildFragmentManager(), topics);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMyTopicsEmpty() {
        showLoading();
    }

    @Override
    public void refreshMyTopics() {
        mPresenter.retrieveMyTopics();
    }


}
