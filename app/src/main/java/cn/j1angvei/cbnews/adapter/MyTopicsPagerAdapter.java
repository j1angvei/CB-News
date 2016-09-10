package cn.j1angvei.cbnews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.fragment.ArticlesFragment;

/**
 * Created by Wayne on 2016/7/6.
 * load fragment to show my topic news
 */
public class MyTopicsPagerAdapter extends FragmentPagerAdapter {
    private final List<Topic> mMyTopics;

    public MyTopicsPagerAdapter(FragmentManager fm, List<Topic> myTopics) {
        super(fm);
        mMyTopics = myTopics;
    }

    @Override
    public Fragment getItem(int position) {
        return ArticlesFragment.newInstance(mMyTopics.get(position).getId());
    }

    @Override
    public int getCount() {
        return mMyTopics.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mMyTopics.get(position).getTitle();
    }
}
