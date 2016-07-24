package cn.j1angvei.cnbetareader.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.bean.SourceType;
import cn.j1angvei.cnbetareader.di.component.DaggerActivityComponent;
import cn.j1angvei.cnbetareader.di.module.ActivityModule;
import cn.j1angvei.cnbetareader.fragment.ExploreFragment;
import cn.j1angvei.cnbetareader.fragment.ReviewFragment;
import cn.j1angvei.cnbetareader.fragment.ArticlesFragment;
import cn.j1angvei.cnbetareader.fragment.HeadlineFragment;
import cn.j1angvei.cnbetareader.fragment.MyTopicsFragment;

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
    protected void initView() {

    }

    @Override
    protected void inject() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        //di
        mActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
        mActivityComponent.inject(this);
        //bind view
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
        mFragmentManager.beginTransaction().add(R.id.fl_container, ArticlesFragment.newInstance("all"), "all").commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                break;
            case R.id.menu_mini_card:
                break;
            default:
                break;
        }
        Toast.makeText(this, "haha", Toast.LENGTH_LONG).show();
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
                tag = SourceType.LATEST_NEWS.getValue();
                fragment = mFragmentManager.findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = ArticlesFragment.newInstance(tag);
                }
                break;
            case R.id.nav_hot_news_comment:
                tag = SourceType.HOT_COMMENT.getValue();
                fragment = mFragmentManager.findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = ReviewFragment.newInstance(tag);
                }
                break;
            case R.id.nav_past_headlines:
                tag = SourceType.PAST_HEADLINE.getValue();
                fragment = mFragmentManager.findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = HeadlineFragment.newInstance(tag);
                }
                break;

            case R.id.nav_my_topics:
                tag = SourceType.MY_TOPICS.getValue();
                fragment = mFragmentManager.findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = new MyTopicsFragment();
                }
                break;
            case R.id.nav_explore:
                tag = SourceType.ALL_TOPICS.getValue();
                int page = 3;
                fragment = mFragmentManager.findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = ExploreFragment.newInstance(3);
                }
                break;
            default:
                break;
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