package cn.j1angvei.cnbetareader.dialog;

import android.view.View;

import cn.j1angvei.cnbetareader.di.component.ActivityComponent;

/**
 * Created by Wayne on 2016/8/26.
 */

public interface BaseDialog {
    void inject(ActivityComponent component);

    void initView(View view);

}
