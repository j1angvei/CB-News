package cn.j1angvei.cnbetareader.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import cn.j1angvei.cnbetareader.bean.News;
import cn.j1angvei.cnbetareader.fragment.ContentFragment;

/**
 * Created by Wayne on 2016/7/25.
 * load fragment to render news content
 */
public class ContentPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> allSid;
    private List<News> mNewses;

    public ContentPagerAdapter(FragmentManager fm, List<News> newses) {
        super(fm);
        mNewses = newses;
    }

    @Override
    public Fragment getItem(int position) {
        String sid = mNewses.get(position).getSid();
        return ContentFragment.newInstance(sid);
    }

    @Override
    public int getCount() {
        return mNewses.size();
    }
}
