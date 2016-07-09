package cn.j1angvei.cnbetareader.activity;

import android.support.v7.app.AppCompatActivity;

import cn.j1angvei.cnbetareader.CBApplication;
import cn.j1angvei.cnbetareader.di.component.ActivityComponent;
import cn.j1angvei.cnbetareader.di.component.ApplicationComponent;

/**
 * Created by Wayne on 2016/6/27.
 */
public class BaseActivity extends AppCompatActivity {
    protected ActivityComponent mActivityComponent;

    protected ApplicationComponent getApplicationComponent() {
        return ((CBApplication) getApplication()).getApplicationComponent();
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }
}
