package cn.j1angvei.cnbetareader.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
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
import cn.j1angvei.cnbetareader.bean.Source;
import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.contract.MyTopicsContract;
import cn.j1angvei.cnbetareader.di.component.ActivityComponent;
import cn.j1angvei.cnbetareader.di.module.FragmentModule;
import cn.j1angvei.cnbetareader.presenter.MyTopicsPresenter;

/**
 * Created by Wayne on 2016/7/6.
 */
public class MyTopicsFragment extends BaseFragment implements MyTopicsContract.View {
    private static final String LATER_USE = "MyTopicsFragment.later_use";
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.tv_my_topic_hint)
    TextView mHint;
    @Inject
    MyTopicsPresenter mPresenter;
    TabLayout mTabLayout;
    MyTopicsPagerAdapter mAdapter;
    FloatingActionButton mFab;

    public static MyTopicsFragment newInstance(String later) {
        MyTopicsFragment fragment = new MyTopicsFragment();
        Bundle args = new Bundle();
        args.putString(LATER_USE, later);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject(((BaseActivity) getActivity()).getActivityComponent());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_topics, container, false);
        ButterKnife.bind(this, view);
        mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mFab.setImageResource(R.drawable.ic_add_white);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prepareAddTopics();
            }
        });
        mTabLayout = (TabLayout) getActivity().findViewById(R.id.tab_layout);
        return view;
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

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void prepareAddTopics() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.fl_container, ExploreFragment.newInstance(1), Source.EXPLORE.toString())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void renderMyTopics(List<Topic> topics) {
        if (topics.isEmpty()) {
            mHint.setVisibility(View.VISIBLE);
            mHint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    prepareAddTopics();
                }
            });
        } else {
            mAdapter = new MyTopicsPagerAdapter(getChildFragmentManager(), topics);
            mViewPager.setAdapter(mAdapter);
            mTabLayout.setupWithViewPager(mViewPager);
            mTabLayout.setVisibility(View.VISIBLE);
            mHint.setVisibility(View.GONE);
        }
    }

    @Override
    public void onMyTopicsEmpty() {

    }
}
