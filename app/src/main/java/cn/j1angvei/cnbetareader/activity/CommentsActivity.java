package cn.j1angvei.cnbetareader.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.bean.Comments;
import cn.j1angvei.cnbetareader.contract.CommentsContract;
import cn.j1angvei.cnbetareader.di.component.DaggerActivityComponent;
import cn.j1angvei.cnbetareader.di.module.ActivityModule;
import cn.j1angvei.cnbetareader.fragment.CommentsFragment;
import cn.j1angvei.cnbetareader.presenter.CommentsPresenter;
import cn.j1angvei.cnbetareader.util.MessageUtil;

/**
 * Created by Wayne on 2016/7/28.
 * activity to handle comments relevant stuff
 */
public class CommentsActivity extends BaseActivity implements CommentsContract.View {
    public static final String NEWS_SID = "CommentsActivity.news_sid";
    public static final String NEWS_SN = "CommentsActivity.news_sn";
    private static final String TAG_ALL_COMMENTS = "CommentsActivity_all";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @Inject
    FragmentManager mFragmentManager;
    @Inject
    CommentsPresenter mPresenter;
    private String mSid, mSn;
    private Comments mComments;

    @Override
    protected void parseIntent() {
        mSid = getIntent().getStringExtra(NEWS_SID);
        mSn = getIntent().getStringExtra(NEWS_SN);
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
                MessageUtil.toast("add comments", view.getContext());
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
        mPresenter.retrieveComments(mSid, mSn);
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

//    @Override
//    public void beforeOperateComment(String action, int position) {
//        CommentItem item = getCommentItem(position);
//        mPresenter.operateComment(position, mToken, action, item.getArticleId(), item.getCommentId());
//    }
//
//    private CommentItem getCommentItem(int position) {
//        String sid = mComments.getAllIds().get(position);
//        return mComments.getCommentMap().get(sid);
//    }
//
//    @Override
//    public void afterOperateSuccess(int position) {
//        MessageUtil.toast("operate success", this);
//        CommentItem item = getCommentItem(position);
//        item.setUpVote(item.getUpVote() + "1");
//        //if pop comment fragment not visible, change count in all comments
//        ((CommentsFragment) mFragmentManager.findFragmentByTag(TAG_ALL_COMMENTS)).notifyCommentItemChanged(position);
//
//        //number should add 1
//    }
//
//    @Override
//    public void afterOperateFail() {
//        MessageUtil.toast("operate failed", this);
//    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

}
