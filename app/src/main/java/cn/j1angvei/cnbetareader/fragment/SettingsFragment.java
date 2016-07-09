package cn.j1angvei.cnbetareader.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import cn.j1angvei.cnbetareader.R;

/**
 * Created by Wayne on 2016/6/28.
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
