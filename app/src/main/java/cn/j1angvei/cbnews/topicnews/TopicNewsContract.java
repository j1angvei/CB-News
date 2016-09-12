package cn.j1angvei.cbnews.topicnews;

import cn.j1angvei.cbnews.base.BasePresenter;
import cn.j1angvei.cbnews.base.BaseView;
import cn.j1angvei.cbnews.bean.Topic;

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
