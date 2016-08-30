package cn.j1angvei.cnbetareader.contract;

import cn.j1angvei.cnbetareader.bean.Topic;

/**
 * Created by Wayne on 2016/8/30.
 */

public interface TopicNewsContract {
    interface View extends BaseView {
        void onAddSuccess();

        void onAddFail();
    }

    interface Presenter extends BasePresenter<View> {
        void addToMyTopics(Topic topic);
    }
}
