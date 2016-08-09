package cn.j1angvei.cnbetareader.activity;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.bean.Source;
import cn.j1angvei.cnbetareader.di.component.DaggerActivityComponent;
import cn.j1angvei.cnbetareader.di.module.ActivityModule;
import cn.j1angvei.cnbetareader.fragment.ArticlesFragment;
import cn.j1angvei.cnbetareader.fragment.BookmarkFragment;
import cn.j1angvei.cnbetareader.fragment.ExploreFragment;
import cn.j1angvei.cnbetareader.fragment.HeadlineFragment;
import cn.j1angvei.cnbetareader.fragment.MyTopicsFragment;
import cn.j1angvei.cnbetareader.fragment.ReviewFragment;
import cn.j1angvei.cnbetareader.util.MessageUtil;

/**
 * Created by Wayne on 2016/7/4.
 */
public class NewsActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Inject
    FragmentManager mFragmentManager;

    @Override
    protected void parseIntent() {
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
        setContentView(R.layout.activity_news_list);
        ButterKnife.bind(this);
        //toolbar
        setSupportActionBar(mToolbar);
        //drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //navigation view
        mNavigationView.setNavigationItemSelectedListener(this);
        //add init fragment
        String tag = Source.LATEST_NEWS.getValue();
        mFragmentManager.beginTransaction().add(R.id.fl_container, ArticlesFragment.newInstance(tag), tag).commit();
        setTitle(R.string.nav_latest_news);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_news_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                MessageUtil.shortToast("search", this);
                break;
            case R.id.menu_mini_card:
                MessageUtil.shortToast("mini card", this);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        Fragment fragment = null;
        String tag = null;
        switch (item.getItemId()) {
            case R.id.nav_latest_news:
                tag = Source.LATEST_NEWS.getValue();
                fragment = mFragmentManager.findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = ArticlesFragment.newInstance(tag);
                }
                break;
            case R.id.nav_hot_news_comment:
                tag = Source.HOT_COMMENT.getValue();
                fragment = mFragmentManager.findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = ReviewFragment.newInstance(tag);
                }
                break;
            case R.id.nav_past_headlines:
                tag = Source.PAST_HEADLINE.getValue();
                fragment = mFragmentManager.findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = HeadlineFragment.newInstance(tag);
                }
                break;

            case R.id.nav_my_topics:
                tag = Source.MY_TOPICS.getValue();
                fragment = mFragmentManager.findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = new MyTopicsFragment();
                }
                break;
            case R.id.nav_explore:
                tag = Source.ALL_TOPICS.getValue();
                int page = 3;
                fragment = mFragmentManager.findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = ExploreFragment.newInstance(3);
                }
                break;
            case R.id.nav_bookmarks:
                tag = Source.BOOKMARKS.getValue();
                fragment = mFragmentManager.findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = BookmarkFragment.newInstance(tag);
                }
                break;
            case R.id.nav_download:
                MessageUtil.shortToast("download", this);
                return true;
            case R.id.nav_settings:
                MessageUtil.shortToast("setting", this);
                return true;
            case R.id.nav_exit:
                MessageUtil.shortToast("exit", this);
                return true;
            default:
                return true;
        }
        assert fragment != null;
        mFragmentManager.beginTransaction()
                .replace(R.id.fl_container, fragment, tag)
                .addToBackStack(null)
                .commit();

        setTitle(item.getTitle());
        return true;
    }


}