package cn.j1angvei.cnbetareader.activity;

import android.content.res.Configuration;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.adapter.ContentPagerAdapter;
import cn.j1angvei.cnbetareader.bean.News;
import cn.j1angvei.cnbetareader.di.component.DaggerActivityComponent;
import cn.j1angvei.cnbetareader.di.module.ActivityModule;
import cn.j1angvei.cnbetareader.util.Navigator;
import cn.j1angvei.cnbetareader.util.NetworkUtil;

/**
 * Created by Wayne on 2016/7/5.
 * display news content
 */
public class ContentActivity extends BaseActivity {
    private static final String TAG = "ContentActivity";
    public static final String NEWS_LIST = "ContentActivity.news_source";
    public static final String NEWS_POSITION = "ContentActivity.news_position";
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    ContentPagerAdapter mAdapter;
    private List<News> mNewses;
    private int mInitPos;
    @Inject
    NetworkUtil mNetworkUtil;

    @Override
    protected void parseIntent() {
        mInitPos = getIntent().getIntExtra(NEWS_POSITION, 0);
        mNewses = getIntent().getParcelableArrayListExtra(NEWS_LIST);
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
        setContentView(R.layout.activity_news_content);
        ButterKnife.bind(this);
        //toolbar
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setTitle(R.string.title_activity_content);
        //fab
        mFab.setImageResource(R.drawable.ic_group_comments_white);
        //viewpager
        mAdapter = new ContentPagerAdapter(getSupportFragmentManager(), mNewses);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                final News news = mNewses.get(position);
                setTitle(news.getTitle());
                mFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Navigator.toComments(news.getSid(), news.getTitle(), v.getContext());
                    }
                });
            }
        });
        mViewPager.setCurrentItem(mInitPos);
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

    public boolean isNightMode() {
        int mode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        return mode == Configuration.UI_MODE_NIGHT_YES;
    }

    public boolean isAutoLoadImage() {
        return mNetworkUtil.isWifiOn();
    }
}
