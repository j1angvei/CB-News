package cn.j1angvei.cnbetareader.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.fragment.TopicNewsFragment;

/**
 * Created by Wayne on 2016/7/6.
 */
public class MyTopicsPagerAdapter extends FragmentPagerAdapter {
    private final List<Topic> mTopics;

    public MyTopicsPagerAdapter(FragmentManager fm, List<Topic> topics) {
        super(fm);
        mTopics = topics;
    }

    @Override
    public Fragment getItem(int position) {
        return TopicNewsFragment.newInstance(mTopics.get(position).getId());
    }

    @Override
    public int getCount() {
        return mTopics.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTopics.get(position).getTitle();
    }
}
