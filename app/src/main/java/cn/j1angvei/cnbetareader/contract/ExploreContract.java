package cn.j1angvei.cnbetareader.contract;

import java.util.Set;

import cn.j1angvei.cnbetareader.bean.Topic;

/**
 * Created by Wayne on 2016/8/9.
 */
public interface ExploreContract {
    interface View extends BaseView {
        void renderTopic(Topic topic);

        void clearTopics();

        void onAddSuccess();

        void onAddFail();
    }

    interface Presenter extends BasePresenter<ExploreContract.View> {
        void retrieveTopics(int page);

        void saveMyTopicIds(Set<String> ids);
    }

}
