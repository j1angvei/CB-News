package cn.j1angvei.cnbetareader.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.adapter.MyTopicsPagerAdapter;
import cn.j1angvei.cnbetareader.di.component.ActivityComponent;

/**
 * Created by Wayne on 2016/7/6.
 */
public class MyTopicsFragment extends BaseFragment {
    private static final String LATER_USE = "MyTopicsFragment.later_use";
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    TabLayout mTabLayout;
    MyTopicsPagerAdapter mAdapter;

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
        mAdapter = new MyTopicsPagerAdapter(getChildFragmentManager());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.include_viewpager, container, false);
        ButterKnife.bind(this, view);
        mViewPager.setAdapter(mAdapter);

        mTabLayout = (TabLayout) getActivity().findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setVisibility(View.VISIBLE);

        return view;
    }

    @Override
    public void onDestroyView() {
        mTabLayout.setVisibility(View.GONE);
        super.onDestroyView();
    }

    @Override
    protected void inject(ActivityComponent component) {

    }
}
