package cn.j1angvei.cnbetareader.contract;

import android.content.Context;

/**
 * Created by Wayne on 2016/6/16.
 */

public interface BaseView {
    void showLoading();

    void hideLoading();

    Context getViewContext();

}
