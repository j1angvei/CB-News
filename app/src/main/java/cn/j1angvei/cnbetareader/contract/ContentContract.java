package cn.j1angvei.cnbetareader.contract;

import cn.j1angvei.cnbetareader.bean.Content;

/**
 * Created by Wayne on 2016/8/9.
 */
public interface ContentContract {
    interface View extends BaseView {
        void renderContent(Content content);

        void onLoadFail(int infoId);
    }

    interface Presenter extends BasePresenter<ContentContract.View> {
        void retrieveContent(String sid);
    }
}
