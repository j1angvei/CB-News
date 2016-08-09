package cn.j1angvei.cnbetareader.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.j1angvei.cnbetareader.fragment.NestedMyTopicsFragment;

/**
 * Created by Wayne on 2016/7/6.
 */
public class MyTopicsPagerAdapter extends FragmentPagerAdapter {
    private final List<String> mTopicIds;

    public MyTopicsPagerAdapter(FragmentManager fm) {
        super(fm);
        mTopicIds = new ArrayList<>(Arrays.asList("4", "9", "444", "261", "18", "428"));
    }

    @Override
    public Fragment getItem(int position) {
        return NestedMyTopicsFragment.newInstance(mTopicIds.get(position));
    }

    @Override
    public int getCount() {
        return mTopicIds.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTopicIds.get(position);
    }
}
