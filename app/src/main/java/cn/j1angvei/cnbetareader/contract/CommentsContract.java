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

        /**
         * child fragment's adapter call this method
         */
        void prepareJudgeComment(String action, int position);

        void onJudgeSuccess(String action, int position);

        void onJudgeFail();
    }

    interface Presenter extends BasePresenter<CommentsContract.View> {
        void retrieveComments(String sid, String sn);

        void judgeComment(String action, String sid, String tid, int position);
    }

}
