package cn.j1angvei.cnbetareader.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.j1angvei.cnbetareader.CBApplication;
import cn.j1angvei.cnbetareader.di.component.ActivityComponent;
import cn.j1angvei.cnbetareader.di.component.ApplicationComponent;

/**
 * Created by Wayne on 2016/6/27.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected ActivityComponent mActivityComponent;

    protected ApplicationComponent getApplicationComponent() {
        return ((CBApplication) getApplication()).getApplicationComponent();
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseIntent();
        doInjection();
        initView();
    }

    protected abstract void parseIntent();

    protected abstract void doInjection();

    protected abstract void initView();

}
