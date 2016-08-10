package cn.j1angvei.cnbetareader.contract;

import android.content.Context;

import cn.j1angvei.cnbetareader.bean.Comments;

/**
 * Created by Wayne on 2016/8/9.
 */
public interface CommentsContract {
    interface View extends BaseView {
        void fetchComments();

        void showComments(Comments comments);

        Context getContext();

        void beforeOperateComment(String action, int position);

        void afterOperateSuccess(int position);

        void afterOperateFail();

    }

    interface Presenter extends BasePresenter<CommentsContract.View> {
        void retrieveComments(String token, String op);

        void operateComment(int position, String... param);//token,action,sid,tid

    }
}
