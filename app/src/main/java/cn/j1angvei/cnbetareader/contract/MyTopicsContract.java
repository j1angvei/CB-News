package cn.j1angvei.cnbetareader.contract;

import java.util.List;

import cn.j1angvei.cnbetareader.bean.Topic;

/**
 * Created by Wayne on 2016/8/19.
 */

public interface MyTopicsContract {
    interface View extends BaseView {

        void prepareAddTopics();

        void renderMyTopics(List<Topic> topics);

        void onMyTopicsEmpty();
    }

    interface Presenter extends BasePresenter<View> {
        void retrieveMyTopics();
    }
}
