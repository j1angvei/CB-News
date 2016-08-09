package cn.j1angvei.cnbetareader.contract;

import cn.j1angvei.cnbetareader.bean.Comments;

/**
 * Created by Wayne on 2016/8/9.
 */
public interface CommentsContract {
    interface View extends BaseView {
        void getComments();

        void setComments(Comments comments);
    }

    interface Presenter extends BasePresenter<CommentsContract.View> {
        void retrieveComments(String token, String op);
    }
}
