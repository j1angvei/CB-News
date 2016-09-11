package cn.j1angvei.cbnews.contract;

import cn.j1angvei.cbnews.bean.Content;

/**
 * Created by Wayne on 2016/8/9.
 */
public interface ContentContract {
    interface View extends BaseView {
        void renderContent(Content content);

        void onLoadFail(int infoId);
    }

    interface Presenter extends BasePresenter<ContentContract.View> {
        void retrieveContent(int page, String sid);
    }
}
