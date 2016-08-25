package cn.j1angvei.cnbetareader.activity;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.di.component.DaggerActivityComponent;
import cn.j1angvei.cnbetareader.di.module.ActivityModule;
import cn.j1angvei.cnbetareader.fragment.ShowCmtFragment;
import cn.j1angvei.cnbetareader.util.Navigator;

/**
 * Created by Wayne on 2016/7/28.
 * activity to handle comments relevant stuff
 */
public class CommentsActivity extends BaseActivity
//        implements ShowCmtContract.View
{
    public static final String NEWS = "CommentsActivity.news";
    public static final String SHOW_CMT = "CommentsActivity_show";
    public static final String PUBLISH_CMT = "CommentsActivity_publish";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    private Content mContent;

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
        //load show comments fragment
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, ShowCmtFragment.newInstance(mContent), SHOW_CMT).commit();
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

//
//    @Override
//    public void onJudgeSuccess(String action, String tid) {
//        CommentItem item = mComments.getCommentMap().get(tid);
//        if (TextUtils.equals(action, Action.SUPPORT.toString())) {
//            int num = 1 + Integer.parseInt(item.getSupport());
//            item.setSupport(String.valueOf(num));
//        } else if (TextUtils.equals(action, Action.AGAINST.toString())) {
//            int num = 1 + Integer.parseInt(item.getAgainst());
//            item.setAgainst(String.valueOf(num));
//        }
//        ShowCmtFragment fragment = (ShowCmtFragment) mFragmentManager.findFragmentByTag(TAG_ALL_COMMENTS);
//        fragment.notifyDataSetChanged();
//        MessageUtil.snack(mCoordinatorLayout, R.string.info_cmt_judge_success);
//    }
//

}
