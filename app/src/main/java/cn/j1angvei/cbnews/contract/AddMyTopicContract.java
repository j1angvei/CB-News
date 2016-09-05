package cn.j1angvei.cbnews.contract;

import java.util.List;

import cn.j1angvei.cbnews.bean.Topic;

/**
 * Created by Wayne on 2016/8/28.
 */

public interface AddMyTopicContract {
    interface View extends BaseView {
        void renderTopics(int groupPosition, List<Topic> topics);

        void onAddMyTopicsSuccess();

    }

    interface Presenter extends BasePresenter<View> {
        void retrieveTopics(int page);

        void addToMyTopics(List<Topic> topics);

    }
}
