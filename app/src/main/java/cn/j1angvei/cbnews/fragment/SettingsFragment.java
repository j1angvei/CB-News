package cn.j1angvei.cbnews.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatDelegate;

import cn.j1angvei.cbnews.R;
import cn.j1angvei.cbnews.util.Navigator;

/**
 * Created by Wayne on 2016/6/28.
 * show all setting preferences
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String PREF_NIGHT_MODE = "pref_night_mode";
    public static final String PREF_DATA_SAVE_MODE = "pref_data_save_mode";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case PREF_NIGHT_MODE:
                boolean isNightMode = sharedPreferences.getBoolean(PREF_NIGHT_MODE, false);
                if (isNightMode) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Navigator.toNewsList(getActivity());
    }
}
