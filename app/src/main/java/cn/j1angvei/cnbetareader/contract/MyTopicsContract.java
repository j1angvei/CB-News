package cn.j1angvei.cnbetareader.contract;

import java.util.List;

import cn.j1angvei.cnbetareader.bean.MyTopic;

/**
 * Created by Wayne on 2016/8/19.
 */

public interface MyTopicsContract {
    interface View extends BaseView {

        void showAllTopics();

        void renderMyTopics(List<MyTopic> topics);

        void onMyTopicsEmpty();

        void refreshMyTopics();
    }

    interface Presenter extends BasePresenter<View> {
        void retrieveMyTopics();
    }
}
