package cn.j1angvei.cnbetareader.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
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

import static cn.j1angvei.cnbetareader.dialog.AddMyTopicDialog.ADD_TOPIC;

/**
 * Created by Wayne on 2016/7/6.
 */
public class MyTopicsFragment extends BaseFragment implements MyTopicsContract.View {
    private static final String TAG = "MyTopicsFragment";
    private static final String LATER_USE = "MyTopicsFragment.later_use";

    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.tv_my_topic_hint)
    TextView mHint;
    @Inject
    MyTopicsPresenter mPresenter;
    TabLayout mTabLayout;
    MyTopicsPagerAdapter mAdapter;
    CoordinatorLayout mCoordinatorLayout;

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
        mPresenter.retrieveMyTopics();
    }

    @Override
    public void onDestroyView() {
        mTabLayout.setVisibility(View.GONE);
        super.onDestroyView();
    }

    @Override
    protected void inject(ActivityComponent component) {
        component.fragmentComponent(new FragmentModule()).inject(this);
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
    public void showAllTopics() {
        new AddMyTopicDialog().show(getChildFragmentManager(), ADD_TOPIC);
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
}
