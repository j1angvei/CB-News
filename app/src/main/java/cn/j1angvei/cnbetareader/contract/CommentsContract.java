package cn.j1angvei.cnbetareader.contract;

import cn.j1angvei.cnbetareader.bean.Comments;

/**
 * Created by Wayne on 2016/8/9.
 */
public interface CommentsContract {
    interface View extends BaseView {

        /**
         * child fragment's adapter to call this
         */
        void refreshComments();

        /**
         * child fragment's adapter render comments
         *
         * @param comments contain CommentsItem
         */
        void showComments(Comments comments);

    }

    interface Presenter extends BasePresenter<CommentsContract.View> {
        void retrieveComments(String token, String sid, String sn);
    }
}
