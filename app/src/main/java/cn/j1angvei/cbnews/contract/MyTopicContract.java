package cn.j1angvei.cbnews.contract;

import java.util.List;

import cn.j1angvei.cbnews.bean.Topic;

/**
 * Created by Wayne on 2016/8/19.
 */

public interface MyTopicContract {
    interface View extends BaseView {

        void showAllTopics();

        void renderMyTopics(List<Topic> topics);

        void onMyTopicsIsEmpty();

        void refreshMyTopics();

    }

    interface Presenter extends BasePresenter<View> {
        void retrieveMyTopics();

        void storeMyTopics();
    }
}
