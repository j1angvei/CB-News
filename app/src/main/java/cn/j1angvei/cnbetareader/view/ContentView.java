package cn.j1angvei.cnbetareader.view;

import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.fragment.ContentFragment;

/**
 * Created by Wayne on 2016/7/21.
 */
public interface ContentView extends BaseView<Content> {

    void getContent(ContentFragment fragment, String sid);

    void setContent(ContentFragment fragment, Content content);

    void saveContent(Content content);
}
