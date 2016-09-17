package cn.j1angvei.cbnews.newscomments;

import cn.j1angvei.cbnews.base.BasePresenter;
import cn.j1angvei.cbnews.base.BaseView;
import cn.j1angvei.cbnews.bean.CommentItem;
import cn.j1angvei.cbnews.bean.Comments;

/**
 * Created by Wayne on 2016/8/9.
 */
public interface CommentContract {
    interface View extends BaseView {
        void refreshComments();

        void renderComments(Comments comments);

        void toJudgeComment(String action, String tid);

        void onJudgeSuccess();

        void onJudgeFail(int resId);

        void replyCmt(CommentItem item);

    }

    interface Presenter extends BasePresenter<View> {
        void retrieveComments(String sid);

        void cache(String sid);

        void refresh(String sid, String sn);

        void judgeComment(String action, String sid, String tid);
    }
}
