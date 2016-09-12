package cn.j1angvei.cbnews.alltopic;

import cn.j1angvei.cbnews.base.BasePresenter;
import cn.j1angvei.cbnews.base.BaseView;
import cn.j1angvei.cbnews.bean.Topic;

/**
 * Created by Wayne on 2016/8/9.
 */
public interface TopicContract {
    interface View extends BaseView {
        void renderTopic(Topic topic);

        void clearTopics();

        void onLetterChosen(int page);

    }

    interface Presenter extends BasePresenter<View> {
        void retrieveTopics(int page);

    }

}
