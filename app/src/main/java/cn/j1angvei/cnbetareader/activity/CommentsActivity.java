package cn.j1angvei.cnbetareader.activity;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
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
import cn.j1angvei.cnbetareader.dialog.PublishCmtDialog;
import cn.j1angvei.cnbetareader.fragment.ShowCmtFragment;

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
                showPublishCmtDialog(true, mContent.getTitle(), mContent.getSid(), "0");
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

    public void showPublishCmtDialog(boolean isAdd, String quote, String sid, String pid) {
        DialogFragment dialog = PublishCmtDialog.newInstance(isAdd, quote, sid, pid);
        dialog.show(getSupportFragmentManager(), PUBLISH_CMT);
    }
}
