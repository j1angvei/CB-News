package cn.j1angvei.cnbetareader.dialog;

import android.support.v4.app.DialogFragment;
import android.view.View;

import cn.j1angvei.cnbetareader.di.component.ActivityComponent;

/**
 * Created by Wayne on 2016/8/26.
 */

public abstract class BaseDialog extends DialogFragment {
    protected abstract void inject(ActivityComponent component);

    abstract void initView(View view);

}
