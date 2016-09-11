package cn.j1angvei.cbnews.activity;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cbnews.R;
import cn.j1angvei.cbnews.di.component.DaggerActivityComponent;
import cn.j1angvei.cbnews.di.module.ActivityModule;
import cn.j1angvei.cbnews.dialog.PublishCmtDialog;
import cn.j1angvei.cbnews.fragment.CommentFragment;

/**
 * Created by Wayne on 2016/7/28.
 * activity to handle comments relevant stuff
 */
public class CommentActivity extends BaseActivity {
    public static final String NEWS_ID = "CommentActivity.news_id";
    public static final String NEWS_TITLE = "CommentActivity.news_title";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    //    private Content mContent;
    private String mSid;
    private String mTitle;

    @Override
    protected void parseIntent() {
//        mContent = getIntent().getParcelableExtra(NEWS);
        mSid = getIntent().getStringExtra(NEWS_ID);
        mTitle = getIntent().getStringExtra(NEWS_TITLE);
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
                showPublishCmtDialog(true, mTitle, mSid, "0");
            }
        });
        //load show comments fragment
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, CommentFragment.newInstance(mSid), "show cmt").commit();
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

    public void showPublishCmtDialog(boolean isAdd, String quote, String sid, String pid) {
        DialogFragment dialog = PublishCmtDialog.newInstance(isAdd, quote, sid, pid);
        dialog.show(getSupportFragmentManager(), "publish cmt");
    }
}
