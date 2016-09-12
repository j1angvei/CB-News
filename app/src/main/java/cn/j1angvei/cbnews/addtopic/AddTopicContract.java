package cn.j1angvei.cbnews.addtopic;

import java.util.List;

import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.base.BasePresenter;
import cn.j1angvei.cbnews.base.BaseView;

/**
 * Created by Wayne on 2016/8/28.
 */

public interface AddTopicContract {
    interface View extends BaseView {
        void renderTopics(int groupPosition, List<Topic> topics);

        void onAddMyTopicsSuccess();

    }

    interface Presenter extends BasePresenter<View> {
        void retrieveTopics(int page);

        void addToMyTopics(List<Topic> topics);

    }
}
