package cn.j1angvei.cnbetareader.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import cn.j1angvei.cnbetareader.fragment.ContentFragment;

/**
 * Created by Wayne on 2016/7/21.
 */
public class NewsContentPagerAdapter extends FragmentPagerAdapter {
    private final List<String> mSids;
    private final Activity mActivity;

    public NewsContentPagerAdapter(FragmentManager fm, Activity activity, List<String> sids) {
        super(fm);
        mActivity = activity;
        mSids = sids;
    }


    @Override
    public Fragment getItem(int position) {
        String sid = mSids.get(position);
        return ContentFragment.newInstance(sid);
    }

    @Override
    public int getCount() {
        return mSids.size();
    }
}
