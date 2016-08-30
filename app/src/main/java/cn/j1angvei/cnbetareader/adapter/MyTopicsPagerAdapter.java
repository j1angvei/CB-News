package cn.j1angvei.cnbetareader.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import cn.j1angvei.cnbetareader.bean.MyTopic;
import cn.j1angvei.cnbetareader.fragment.ArticlesFragment;

/**
 * Created by Wayne on 2016/7/6.
 * load fragment to show my topic news
 */
public class MyTopicsPagerAdapter extends FragmentPagerAdapter {
    private final List<MyTopic> mMyTopics;

    public MyTopicsPagerAdapter(FragmentManager fm, List<MyTopic> myTopics) {
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
