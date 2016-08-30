package cn.j1angvei.cnbetareader.contract;

import cn.j1angvei.cnbetareader.bean.CommentItem;
import cn.j1angvei.cnbetareader.bean.Comments;

/**
 * Created by Wayne on 2016/8/9.
 */
public interface ShowCmtContract {
    interface View extends BaseView {
        void refreshComments();

        void renderComments(Comments comments);

        void toJudgeComment(String action, String tid);

        void onJudgeSuccess();

        void onJudgeFail();

        void replyCmt(CommentItem item);

    }

    interface Presenter extends BasePresenter<View> {
        void retrieveComments(String sid);

        void judgeComment(String action, String sid, String tid);
    }
}
