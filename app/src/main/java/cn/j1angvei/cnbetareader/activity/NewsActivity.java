package cn.j1angvei.cnbetareader.activity;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.bean.Source;
import cn.j1angvei.cnbetareader.contract.BaseView;
import cn.j1angvei.cnbetareader.data.remote.CnbetaApi;
import cn.j1angvei.cnbetareader.di.component.DaggerActivityComponent;
import cn.j1angvei.cnbetareader.di.module.ActivityModule;
import cn.j1angvei.cnbetareader.fragment.ArticlesFragment;
import cn.j1angvei.cnbetareader.fragment.BookmarkFragment;
import cn.j1angvei.cnbetareader.fragment.ExploreFragment;
import cn.j1angvei.cnbetareader.fragment.HeadlineFragment;
import cn.j1angvei.cnbetareader.fragment.MyTopicsFragment;
import cn.j1angvei.cnbetareader.fragment.ReviewFragment;
import cn.j1angvei.cnbetareader.util.ApiUtil;
import cn.j1angvei.cnbetareader.util.MessageUtil;
import cn.j1angvei.cnbetareader.util.PrefsUtil;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static cn.j1angvei.cnbetareader.bean.Source.ALL;
import static cn.j1angvei.cnbetareader.bean.Source.BOOKMARKS;
import static cn.j1angvei.cnbetareader.bean.Source.EDITORCOMMEND;
import static cn.j1angvei.cnbetareader.bean.Source.EXPLORE;
import static cn.j1angvei.cnbetareader.bean.Source.JHCOMMENT;
import static cn.j1angvei.cnbetareader.bean.Source.MY_TOPICS;

/**
 * Created by Wayne on 2016/7/4.
 * control child fragment to display different {@link Source} news
 */
public class NewsActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, BaseView {
    private static final String TAG = "NewsActivity";
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
    CnbetaApi mCnbetaApi;
    @Inject
    PrefsUtil mPrefsUtil;
    boolean mIsTokenValid;

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
        mIsTokenValid = !TextUtils.isEmpty(mPrefsUtil.readToken());
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
        //load init fragment aka latest news
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
            case R.id.menu_search:
                MessageUtil.toast("search", this);
                break;
            case R.id.menu_mini_card:
                MessageUtil.toast("mini card", this);
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
            case R.id.nav_explore:
                source = EXPLORE;
                break;
            case R.id.nav_bookmarks:
                source = BOOKMARKS;
                break;
            case R.id.nav_download:
                MessageUtil.toast("download", this);
                return true;
            case R.id.nav_settings:
                MessageUtil.toast("setting", this);
                return true;
            case R.id.nav_exit:
                MessageUtil.toast("exit", this);
                return true;
            default:
                return true;
        }
        checkToken(source, item.getTitle());
        return true;
    }

    private void loadNewsFragment(Source source, CharSequence title) {
        String type = "" + source;
        Fragment fragment = mFragmentManager.findFragmentByTag(type);
        if (fragment == null) {
            switch (source) {
                case ALL:
                    fragment = ArticlesFragment.newInstance(type);
                    break;
                case JHCOMMENT:
                    fragment = ReviewFragment.newInstance(type);
                    break;
                case EDITORCOMMEND:
                    fragment = HeadlineFragment.newInstance(type);
                    break;
                case EXPLORE:
                    fragment = ExploreFragment.newInstance(1);
                    break;
                case MY_TOPICS:
                    fragment = MyTopicsFragment.newInstance(type);
                    break;
                case BOOKMARKS:
                    fragment = BookmarkFragment.newInstance(type);
                    break;
            }
        }
        setTitle(title);
        mFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.fl_container, fragment, type)
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
        mCnbetaApi.getCsrfToken()
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
            MessageUtil.snackWithAction(mCoordinatorLayout, R.string.snack_info_connection_error,
                    R.string.snack_action_retry, new View.OnClickListener() {
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
                MessageUtil.snack(mCoordinatorLayout, token);
                mPrefsUtil.writeToken(token);
            } catch (IOException e) {
                //something wrong with response
                Log.e(TAG, "onNext: " + body, e);
            }
        }
    }
}