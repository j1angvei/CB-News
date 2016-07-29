package cn.j1angvei.cnbetareader.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.di.component.DaggerActivityComponent;
import cn.j1angvei.cnbetareader.di.module.ActivityModule;
import cn.j1angvei.cnbetareader.fragment.CommentsFragment;
import cn.j1angvei.cnbetareader.util.MessageUtil;

/**
 * Created by Wayne on 2016/7/28.
 */
public class CommentsActivity extends BaseActivity {
    public static final String NEWS_SID = "CommentsActivity.news_sid";
    public static final String NEWS_SN = "CommentsActivity.news_sn";
    public static final String NEWS_TOKEN = "CommentsActivity.news_token";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @Inject
    FragmentManager mFragmentManager;

    private String mCsrfToken;
    private String mOp;

    @Override
    protected void parseIntent() {
        String sid = getIntent().getStringExtra(NEWS_SID);
        String sn = getIntent().getStringExtra(NEWS_SN);
        mOp = String.format("1,%s,%s", sid, sn);
        mCsrfToken = getIntent().getStringExtra(NEWS_TOKEN);
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
        //toolbar
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(R.string.title_activity_comments);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageUtil.shortToast("add comments", view.getContext());
            }
        });
        //load fragment
        mFragmentManager.beginTransaction().add(R.id.fl_container, CommentsFragment.newInstance(mCsrfToken, mOp)).commit();
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
}
