package cn.j1angvei.cnbetareader.contract;

import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.fragment.ContentFragment;

/**
 * Created by Wayne on 2016/8/9.
 */
public interface ContentContract {
    interface View extends BaseView {
        void getContent(ContentFragment fragment, String sid);

        void setContent(ContentFragment fragment, Content content);

        void saveContent(Content content);
    }

    interface Presenter extends BasePresenter<ContentContract.View> {
        void retrieveContent(final ContentFragment fragment, String sid);
    }
}
