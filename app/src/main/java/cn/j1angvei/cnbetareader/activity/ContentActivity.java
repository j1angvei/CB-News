package cn.j1angvei.cnbetareader.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.adapter.ContentPagerAdapter;
import cn.j1angvei.cnbetareader.di.component.DaggerActivityComponent;
import cn.j1angvei.cnbetareader.di.module.ActivityModule;

/**
 * Created by Wayne on 2016/7/5.
 */
public class ContentActivity extends BaseActivity {
    public static final String NEWS_POSITION = "ContentActivity.news_position";
    public static final String NEWS_SIDS = "ContentActivity.news_source";

    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @Inject
    ContentPagerAdapter mAdapter;

    private int mInitPos;
    private List<String> allSid;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //parse intent
        mInitPos = getIntent().getIntExtra(NEWS_POSITION, 0);
        allSid = getIntent().getStringArrayListExtra(NEWS_SIDS);
        //inject
        mActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
        mActivityComponent.inject(this);
        setContentView(R.layout.activity_news_content);
        //init view
        ButterKnife.bind(this);
        //toolbar
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        //viewpager
        mViewPager.setAdapter(mAdapter);
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


    @Override
    protected void initView() {

    }

    @Override
    protected void inject() {

    }

    public List<String> getAllSid() {
        return allSid;
    }
}
