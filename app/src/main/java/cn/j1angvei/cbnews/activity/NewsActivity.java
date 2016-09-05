package cn.j1angvei.cbnews.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cbnews.R;
import cn.j1angvei.cbnews.bean.Source;
import cn.j1angvei.cbnews.contract.BaseView;
import cn.j1angvei.cbnews.data.remote.api.CBApiWrapper;
import cn.j1angvei.cbnews.di.component.DaggerActivityComponent;
import cn.j1angvei.cbnews.di.module.ActivityModule;
import cn.j1angvei.cbnews.fragment.AllTopicsFragment;
import cn.j1angvei.cbnews.fragment.ArticlesFragment;
import cn.j1angvei.cbnews.fragment.BookmarkFragment;
import cn.j1angvei.cbnews.fragment.HeadlineFragment;
import cn.j1angvei.cbnews.fragment.MyTopicsFragment;
import cn.j1angvei.cbnews.fragment.ReviewFragment;
import cn.j1angvei.cbnews.util.ApiUtil;
import cn.j1angvei.cbnews.util.AppUtil;
import cn.j1angvei.cbnews.util.MessageUtil;
import cn.j1angvei.cbnews.util.Navigator;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static cn.j1angvei.cbnews.bean.Source.ALL;
import static cn.j1angvei.cbnews.bean.Source.ALL_TOPICS;
import static cn.j1angvei.cbnews.bean.Source.BOOKMARK;
import static cn.j1angvei.cbnews.bean.Source.EDITORCOMMEND;
import static cn.j1angvei.cbnews.bean.Source.JHCOMMENT;
import static cn.j1angvei.cbnews.bean.Source.MY_TOPICS;

/**
 * Created by Wayne on 2016/7/4.
 * control child fragment to display different {@link Source} news
 */
public class NewsActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, BaseView {
    private static final String TAG = "NewsActivity";
    public static final String EXIT = "NewsActivity.exit";
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    @Inject
    FragmentManager mFragmentManager;
    @Inject
    CBApiWrapper mApiWrapper;
    @Inject
    AppUtil mAppUtil;
    boolean mIsTokenValid;
    private boolean mIsExit;

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
        mIsTokenValid = mAppUtil.isCsrfTokenValid();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_news_list);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
        String title = getResources().getString(R.string.nav_latest_news);
        checkToken(ALL, title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_news_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_mini_card:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            boolean isExit = intent.getBooleanExtra(EXIT, false);
            if (isExit) {
                this.finish();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
            if (mIsExit) {
                this.finish();
            } else {
                MessageUtil.toast(R.string.info_press_to_exit, this);
                mIsExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsExit = false;
                    }
                }, 2000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        Source source;
        switch (item.getItemId()) {
            case R.id.nav_latest_news:
                source = ALL;
                break;
            case R.id.nav_hot_news_comment:
                source = JHCOMMENT;
                break;
            case R.id.nav_past_headlines:
                source = EDITORCOMMEND;
                break;
            case R.id.nav_my_topics:
                source = MY_TOPICS;
                break;
            case R.id.nav_all_topic:
                source = ALL_TOPICS;
                break;
            case R.id.nav_bookmarks:
                source = BOOKMARK;
                break;
            case R.id.nav_download:
                Navigator.toOfflineDownload(this);
                return true;
            case R.id.nav_settings:
                Navigator.toSettings(this);
                return true;
            case R.id.nav_switch_theme:
                mAppUtil.switchCurrentTheme();
                recreate();
                return true;
            default:
                return true;
        }
        checkToken(source, item.getTitle());
        return true;
    }

    private void loadNewsFragment(Source source, CharSequence title) {
        String sourceType = "" + source;
        Fragment fragment = mFragmentManager.findFragmentByTag(sourceType);
        if (fragment == null) {
            switch (source) {
                case ALL:
                    fragment = ArticlesFragment.newInstance(sourceType);
                    break;
                case JHCOMMENT:
                    fragment = ReviewFragment.newInstance(sourceType);
                    break;
                case EDITORCOMMEND:
                    fragment = HeadlineFragment.newInstance(sourceType);
                    break;
                case ALL_TOPICS:
                    fragment = AllTopicsFragment.newInstance(1);
                    break;
                case MY_TOPICS:
                    fragment = MyTopicsFragment.newInstance(sourceType);
                    break;
                case BOOKMARK:
                    fragment = BookmarkFragment.newInstance(sourceType);
                    break;
            }
        }
        setTitle(title);
        mFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.fl_container, fragment, sourceType)
                .addToBackStack(null)
                .commit();
    }

    /**
     * Load specific fragment only if csrf_token is valid
     *
     * @param source represents news source
     * @param title  after fragment is loaded, change the toolbar title
     */
    private void checkToken(final Source source, final CharSequence title) {
        //check if token already stored
        if (mIsTokenValid) {
            loadNewsFragment(source, title);
            return;
        }
        //token not set, retrieve it now
        showLoading();
        mApiWrapper.getCsrfToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CsrfTokenSubscriber(source, title));
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
        return this;
    }

    private class CsrfTokenSubscriber extends Subscriber<ResponseBody> {
        private final Source source;
        private final CharSequence title;

        CsrfTokenSubscriber(Source source, CharSequence title) {
            this.source = source;
            this.title = title;
        }

        @Override
        public void onCompleted() {
            hideLoading();
            mIsTokenValid = true;
            loadNewsFragment(source, title);
        }

        @Override
        public void onError(Throwable e) {
            hideLoading();
            MessageUtil.snackWithAction(mCoordinatorLayout, R.string.info_connection_error,
                    R.string.info_retry, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            checkToken(source, title);
                        }
                    });
        }

        @Override
        public void onNext(ResponseBody body) {
            try {
                String token = ApiUtil.parseToken(body.string());
                mAppUtil.initCsrfToken(token);
            } catch (IOException e) {
                //something wrong with response
            }
        }
    }
}