package cn.j1angvei.cbnews.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.j1angvei.cbnews.CBApplication;
import cn.j1angvei.cbnews.di.component.ActivityComponent;
import cn.j1angvei.cbnews.di.component.ApplicationComponent;

/**
 * Created by Wayne on 2016/6/27.
 * baseActivity with abstract class
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseIntent();
        doInjection();
        initView();
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((CBApplication) getApplication()).getApplicationComponent();
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    protected abstract void parseIntent();

    protected abstract void doInjection();

    protected abstract void initView();

}
