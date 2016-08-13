package cn.j1angvei.cnbetareader.activity;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.bean.CommentAction;
import cn.j1angvei.cnbetareader.bean.CommentItem;
import cn.j1angvei.cnbetareader.bean.Comments;
import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.contract.CommentsContract;
import cn.j1angvei.cnbetareader.di.component.DaggerActivityComponent;
import cn.j1angvei.cnbetareader.di.module.ActivityModule;
import cn.j1angvei.cnbetareader.fragment.CommentsFragment;
import cn.j1angvei.cnbetareader.presenter.CommentsPresenter;
import cn.j1angvei.cnbetareader.util.MessageUtil;
import cn.j1angvei.cnbetareader.util.Navigator;

/**
 * Created by Wayne on 2016/7/28.
 * activity to handle comments relevant stuff
 */
public class CommentsActivity extends BaseActivity implements CommentsContract.View {
    public static final String NEWS = "CommentsActivity.news";
    private static final String TAG_ALL_COMMENTS = "CommentsActivity_all";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    @Inject
    FragmentManager mFragmentManager;
    @Inject
    CommentsPresenter mPresenter;
    private Content mContent;
    private Comments mComments;

    @Override
    protected void parseIntent() {
        mContent = getIntent().getParcelableExtra(NEWS);
    }

    @Override
    protected void doInjection() {
        mActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mPresenter.setView(this);
        setContentView(R.layout.activity_news_comments);
        ButterKnife.bind(this);
        //toolbar
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(R.string.title_activity_comments);
        //fab
        mFab.setImageResource(R.drawable.ic_add_white);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add comment, set pid "0"
                Navigator.toPublishComment(true, mContent.getTitle(), mContent.getSid(), "0", view.getContext());
            }
        });
        //load data aka comments
        refreshComments();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void refreshComments() {
        mPresenter.retrieveComments(mContent.getSid(), mContent.getSn());
    }

    @Override
    public void showComments(Comments comments) {
        if (comments != null) {
            mComments = comments;
            Fragment fragment = mFragmentManager.findFragmentByTag(TAG_ALL_COMMENTS);
            if (fragment == null) {
                fragment = CommentsFragment.newInstance(comments);
                mFragmentManager.beginTransaction().add(R.id.fl_container, fragment, TAG_ALL_COMMENTS).commit();
            } else {
                ((CommentsFragment) fragment).notifyDataSetChanged();
            }
        } else {
            MessageUtil.toast("comments is null", this);
        }
    }

    @Override
    public void prepareJudgeComment(String action, String tid) {
        CommentItem item = mComments.getCommentMap().get(tid);
        mPresenter.judgeComment(action, item.getSid(), tid);
    }

    @Override
    public void onJudgeSuccess(String action, String tid) {
        CommentItem item = mComments.getCommentMap().get(tid);
        if (TextUtils.equals(action, CommentAction.SUPPORT.toString())) {
            int num = 1 + Integer.parseInt(item.getSupport());
            item.setSupport(String.valueOf(num));
        } else if (TextUtils.equals(action, CommentAction.AGAINST.toString())) {
            int num = 1 + Integer.parseInt(item.getAgainst());
            item.setAgainst(String.valueOf(num));
        }
        CommentsFragment fragment = (CommentsFragment) mFragmentManager.findFragmentByTag(TAG_ALL_COMMENTS);
        fragment.notifyDataSetChanged();
        MessageUtil.snack(mCoordinatorLayout, R.string.info_cmt_judge_success);
    }

    @Override
    public void onJudgeFail() {
        MessageUtil.snack(mCoordinatorLayout, R.string.info_cmt_judge_fail);
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

}
