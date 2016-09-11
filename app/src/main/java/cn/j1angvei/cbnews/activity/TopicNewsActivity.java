package cn.j1angvei.cbnews.activity;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cbnews.R;
import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.contract.TopicNewsContract;
import cn.j1angvei.cbnews.di.component.DaggerActivityComponent;
import cn.j1angvei.cbnews.di.module.ActivityModule;
import cn.j1angvei.cbnews.fragment.ArticleFragment;
import cn.j1angvei.cbnews.util.MessageUtil;

/**
 * Created by Wayne on 2016/8/30.
 * show topic news
 */

public class TopicNewsActivity extends BaseActivity implements TopicNewsContract.View {
    public static final String TOPIC = "TopicNewsActivity";
    @BindView(R.id.iv_backdrop)
    ImageView ivBackdrop;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
//    @Inject
//    TopicNewsPresenter mPresenter;
    Topic mTopic;

    @Override
    protected void parseIntent() {
        mTopic = getIntent().getParcelableExtra(TOPIC);
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
//        mPresenter.setView(this);
        setContentView(R.layout.activity_topic_news);
        ButterKnife.bind(this);
        //toolbar
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        //collapsing toolbar
        mCollapsingToolbarLayout.setTitle(mTopic.getTitle());
        Glide.with(this).load(mTopic.getThumb()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                ivBackdrop.setImageBitmap(resource);
                toPaletteColor(resource);
            }
        });
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mPresenter.addToMyTopics(mTopic);
            }
        });

        ArticleFragment fragment = ArticleFragment.newInstance(mTopic.getId());
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, fragment).commit();

    }

    private void toPaletteColor(Bitmap bitmap) {
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                int primaryColor = getResources().getColor(R.color.colorPrimary);
                int accentColor = getResources().getColor(R.color.colorAccent);
                int muteColor = palette.getLightMutedColor(primaryColor);
                int vibrantColor = palette.getVibrantColor(accentColor);
                if (muteColor != primaryColor && vibrantColor != accentColor) {
                    mCollapsingToolbarLayout.setBackgroundColor(muteColor);
                    mFab.setBackgroundTintList(ColorStateList.valueOf(vibrantColor));
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_topic_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_mini_card:
                break;
            case R.id.menu_add_to_my_topic:
                break;
            case android.R.id.home:
                onBackPressed();
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAddSuccess() {
        MessageUtil.snack(mCoordinatorLayout, "Add success");
    }

    @Override
    public void onAddFail() {
        MessageUtil.snack(mCoordinatorLayout, "add fail");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public Context getViewContext() {
        return null;
    }
}
