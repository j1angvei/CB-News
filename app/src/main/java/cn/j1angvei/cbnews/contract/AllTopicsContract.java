package cn.j1angvei.cbnews.contract;

import cn.j1angvei.cbnews.bean.Topic;

/**
 * Created by Wayne on 2016/8/9.
 */
public interface AllTopicsContract {
    interface View extends BaseView {
        void renderTopic(Topic topic);

        void clearTopics();

        void onLetterChosen(int page);

    }

    interface Presenter extends BasePresenter<AllTopicsContract.View> {
        void retrieveTopics(int page);

    }

}
