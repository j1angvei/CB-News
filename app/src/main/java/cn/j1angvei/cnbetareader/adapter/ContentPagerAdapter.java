package cn.j1angvei.cnbetareader.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import javax.inject.Inject;

import cn.j1angvei.cnbetareader.activity.ContentActivity;
import cn.j1angvei.cnbetareader.di.scope.PerActivity;
import cn.j1angvei.cnbetareader.fragment.ContentFragment;

/**
 * Created by Wayne on 2016/7/25.
 */
@PerActivity
public class ContentPagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "ContentPagerAdapter";

    private ContentActivity mActivity;
    private List<String> allSid;

    @Inject
    public ContentPagerAdapter(FragmentManager fm, Activity activity) {
        super(fm);
        mActivity = (ContentActivity) activity;
        allSid = mActivity.getAllSid();

    }

    @Override
    public Fragment getItem(int position) {
        return ContentFragment.newInstance(allSid.get(position));
    }

    @Override
    public int getCount() {
        return allSid.size();
    }
}
