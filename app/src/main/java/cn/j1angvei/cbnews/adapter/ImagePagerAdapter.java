package cn.j1angvei.cbnews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import cn.j1angvei.cbnews.fragment.ImageFragment;

/**
 * Created by Wayne on 2016/9/4.
 */

public class ImagePagerAdapter extends FragmentStatePagerAdapter {
    private String[] mUrls;

    public ImagePagerAdapter(FragmentManager fm, String[] urls) {
        super(fm);
        mUrls = urls;
    }

    @Override
    public Fragment getItem(int position) {
        String url = mUrls[position];
        return ImageFragment.newInstance(url);
    }

    @Override
    public int getCount() {
        return mUrls.length;
    }
}
