package cn.j1angvei.cnbetareader.activity;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.adapter.ContentPagerAdapter;
import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.contract.ContentContract;
import cn.j1angvei.cnbetareader.di.component.DaggerActivityComponent;
import cn.j1angvei.cnbetareader.di.module.ActivityModule;
import cn.j1angvei.cnbetareader.fragment.ContentFragment;
import cn.j1angvei.cnbetareader.presenter.ContentPresenter;
import cn.j1angvei.cnbetareader.util.MessageUtil;
import cn.j1angvei.cnbetareader.util.Navigator;

/**
 * Created by Wayne on 2016/7/5.
 * display news content
 */
public class ContentActivity extends BaseActivity implements ContentContract.View {
    public static final String NEWS_POSITION = "ContentActivity.news_position";
    public static final String NEWS_SIDS = "ContentActivity.news_source";

    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @Inject
    ContentPagerAdapter mAdapter;
    @Inject
    ContentPresenter mPresenter;

    private int mInitPos;
    private List<String> mSids;
    private Map<String, Content> mContentMap = new HashMap<>();

    @Override
    protected void parseIntent() {
        mInitPos = getIntent().getIntExtra(NEWS_POSITION, 0);
        mSids = getIntent().getStringArrayListExtra(NEWS_SIDS);
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
        setContentView(R.layout.activity_news_content);
        ButterKnife.bind(this);
        //toolbar
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(R.string.title_activity_content);
        //fab
        mFab.setImageResource(R.drawable.ic_group_comments_white);
        //viewpager
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(final int position) {
                mFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Content content = mContentMap.get(mSids.get(position));
                        if (content != null) {
                            Navigator.toComments(content, view.getContext());
                        } else {
                            MessageUtil.toast("content is null", view.getContext());
                        }
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

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public Context getViewContext() {
        return null;
    }


    public List<String> getSids() {
        return mSids;
    }

    @Override
    public void getContent(ContentFragment fragment, String sid) {
        mPresenter.retrieveContent(fragment, sid);
    }

    @Override
    public void setContent(ContentFragment fragment, Content content) {
        fragment.renderItem(content);
    }

    @Override
    public void saveContent(Content content) {
        mContentMap.put(content.getSid(), content);
    }

}
