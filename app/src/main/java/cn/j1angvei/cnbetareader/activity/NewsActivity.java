package cn.j1angvei.cnbetareader.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import cn.j1angvei.cnbetareader.data.remote.api.CnbetaApi;
import cn.j1angvei.cnbetareader.di.component.DaggerActivityComponent;
import cn.j1angvei.cnbetareader.di.module.ActivityModule;
import cn.j1angvei.cnbetareader.fragment.AllTopicsFragment;
import cn.j1angvei.cnbetareader.fragment.ArticlesFragment;
import cn.j1angvei.cnbetareader.fragment.BookmarkFragment;
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
import static cn.j1angvei.cnbetareader.bean.Source.ALL_TOPICS;
import static cn.j1angvei.cnbetareader.bean.Source.BOOKMARK;
import static cn.j1angvei.cnbetareader.bean.Source.EDITORCOMMEND;
import static cn.j1angvei.cnbetareader.bean.Source.JHCOMMENT;
import static cn.j1angvei.cnbetareader.bean.Source.MY_TOPICS;

/**
 * Created by Wayne on 2016/7/4.
 * control child fragment to display different {@link Source} news
 */
public class NewsActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, BaseView {
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
        mIsTokenValid = !TextUtils.isEmpty(mPrefsUtil.readString(PrefsUtil.CSRF_TOKEN));
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
            case R.id.menu_mini_card:
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
                return true;
            case R.id.nav_settings:
                return true;
            case R.id.nav_exit:
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
                mPrefsUtil.writeString(PrefsUtil.CSRF_TOKEN, token);
            } catch (IOException e) {
                //something wrong with response
            }
        }
    }
}