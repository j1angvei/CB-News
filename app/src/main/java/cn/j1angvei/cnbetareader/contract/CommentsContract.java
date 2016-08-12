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
         * initiate fragment {@link cn.j1angvei.cnbetareader.fragment.CommentsFragment}to render comments
         */
        void showComments(Comments comments);

        /**
         * judge comment item, such as support, against, report,
         * publish comment can be add new comment, reply comment
         */
        void prepareJudgeComment(String action, String tid);

        void onJudgeSuccess(String action, String tid);

        void onJudgeFail();

        void preparePublishComment(String tid);

        /**
         * load dialog to publish comment
         */
        void showPublishComment(boolean isAdd, String quote);
    }

    interface Presenter extends BasePresenter<CommentsContract.View> {
        void retrieveComments(String sid, String sn);

        void judgeComment(String action, String sid, String tid);

        void publishComment();
    }

}
