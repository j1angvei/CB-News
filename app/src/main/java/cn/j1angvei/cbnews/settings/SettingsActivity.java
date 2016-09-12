package cn.j1angvei.cbnews.settings;

import cn.j1angvei.cbnews.base.BaseActivity;

/**
 * Created by Wayne on 2016/6/28.
 */
public class SettingsActivity extends BaseActivity {
    @Override
    protected void parseIntent() {
    }

    @Override
    protected void doInjection() {
    }

    @Override
    protected void initView() {
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
