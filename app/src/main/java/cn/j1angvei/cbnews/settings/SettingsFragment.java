package cn.j1angvei.cbnews.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;

import cn.j1angvei.cbnews.R;

/**
 * Created by Wayne on 2016/6/28.
 * show all setting preferences
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String PREF_NIGHT_MODE = "pref_night_mode";
    public static final String PREF_DATA_SAVE_MODE = "pref_data_save_mode";
    public static final String PREF_AUTO_REFRESH = "pref_auto_refresh";
    public static final String PREF_CMT_TAIL = "pref_cmt_tail";

    EditTextPreference mCmtTail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCmtTail = (EditTextPreference) getPreferenceManager().findPreference(PREF_CMT_TAIL);
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
            case PREF_CMT_TAIL:
                mCmtTail.setSummary(mCmtTail.getText());
                break;
            default:
                break;
        }
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
}
