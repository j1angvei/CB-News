package cn.j1angvei.cnbetareader.view;

import cn.j1angvei.cnbetareader.bean.Topic;

/**
 * Created by Wayne on 2016/7/22.
 */
public interface TopicView extends BaseView {
    void renderItem(Topic topic);

    void clearItems();
}
