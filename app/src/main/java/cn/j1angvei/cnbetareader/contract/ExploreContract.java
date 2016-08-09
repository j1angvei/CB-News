package cn.j1angvei.cnbetareader.contract;

import cn.j1angvei.cnbetareader.bean.Topic;

/**
 * Created by Wayne on 2016/8/9.
 */
public interface ExploreContract {
    interface View extends BaseView {
        void renderTopic(Topic topic);

        void clearTopics();
    }

    interface Presenter extends BasePresenter<ExploreContract.View> {
        void retrieveTopics(int index);
    }

}
