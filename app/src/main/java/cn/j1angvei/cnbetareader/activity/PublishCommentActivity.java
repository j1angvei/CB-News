package cn.j1angvei.cnbetareader.activity;

import android.graphics.Bitmap;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.contract.PublishCommentContract;
import cn.j1angvei.cnbetareader.di.component.DaggerActivityComponent;
import cn.j1angvei.cnbetareader.di.module.ActivityModule;
import cn.j1angvei.cnbetareader.presenter.PublishCommentPresenter;
import cn.j1angvei.cnbetareader.util.MessageUtil;

/**
 * Created by Wayne on 2016/8/13.
 */
public class PublishCommentActivity extends BaseActivity implements PublishCommentContract.View {
    public static final String IS_ADD = "PublishCommentActivity.is_add";
    public static final String QUOTE = "PublishCommentActivity.quote";
    public static final String PID = "PublishCommentActivity.pid";
    public static final String SID = "PublishCommentActivity.sid";
    @BindView(R.id.tv_publish_cmt_header)
    TextView tvHeader;
    @BindView(R.id.tv_publish_cmt_quote)
    TextView tvQuote;
    @BindView(R.id.et_publish_cmt_content)
    EditText etContent;
    @BindView(R.id.et_publish_cmt_captcha)
    EditText etCaptcha;
    @BindView(R.id.iv_publish_cmt_captcha)
    ImageView ivCaptcha;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.btn_publish_cmt_send)
    Button btnSend;
    private boolean mIsAdd;
    private String mQuoteContent;
    private String mPid;
    private String mSid;

    @Inject
    PublishCommentPresenter mPresenter;

    @Override
    protected void parseIntent() {
        mIsAdd = getIntent().getBooleanExtra(IS_ADD, false);
        mQuoteContent = getIntent().getStringExtra(QUOTE);
        mPid = getIntent().getStringExtra(PID);
        mSid = getIntent().getStringExtra(SID);
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
        setContentView(R.layout.activity_comment_publish);
        ButterKnife.bind(this);
        //toolbar
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_white);
        //set title and quote content
        setTitle(mIsAdd ? R.string.title_publish_cmt_add : R.string.title_publish_cmt_reply);
        tvHeader.setText(mIsAdd ? R.string.header_publish_cmt_news_title : R.string.header_publish_cmt_origin_cmt);
        tvQuote.setText(mQuoteContent);
        //set captcha image
        ivCaptcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivCaptcha.setImageDrawable(null);
                mPresenter.getCaptchaImage(mSid);
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = etContent.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    MessageUtil.toast("no comment", view.getContext());
                    return;
                }
                String captcha = etCaptcha.getText().toString();
                if (TextUtils.isEmpty(captcha)) {
                    MessageUtil.toast("no captcha", view.getContext());
                    return;
                }
                mPresenter.sendComment(etContent.getText().toString(), etCaptcha.getText().toString(), mSid, mPid);
            }
        });
        mPresenter.getCaptchaImage(mSid);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_publish_cmt, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_publish_cmt_send:
                MessageUtil.toast("send", this);
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showCaptcha(Bitmap bitmap) {
        ivCaptcha.setImageBitmap(bitmap);
    }

    @Override
    public void onShowCaptchaFail() {
        MessageUtil.snack(mCoordinatorLayout, "load captcha fail");
    }

    @Override
    public void showInfo(String message) {
        MessageUtil.snack(mCoordinatorLayout, message);
    }

    @Override
    public void onSendFail() {
        MessageUtil.snack(mCoordinatorLayout, "Send failed");
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }
}
