package cn.j1angvei.cbnews.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.j1angvei.cbnews.R;
import cn.j1angvei.cbnews.util.Navigator;

/**
 * Created by Wayne on 2016/9/4.
 * splash screen show web logo
 */

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = this;
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Navigator.toNewsList(context);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
