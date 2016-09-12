package cn.j1angvei.cbnews.newscontent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cbnews.R;
import cn.j1angvei.cbnews.base.BaseActivity;
import cn.j1angvei.cbnews.bean.Content;
import cn.j1angvei.cbnews.base.BaseFragment;
import cn.j1angvei.cbnews.di.component.ActivityComponent;
import cn.j1angvei.cbnews.di.module.FragmentModule;
import cn.j1angvei.cbnews.util.ApiUtil;
import cn.j1angvei.cbnews.util.DateUtil;
import cn.j1angvei.cbnews.util.HtmlUtil;
import cn.j1angvei.cbnews.util.MessageUtil;
import cn.j1angvei.cbnews.util.Navigator;
import cn.j1angvei.cbnews.util.NetworkUtil;
import cn.j1angvei.cbnews.web.JsInterface;

/**
 * Created by Wayne on 2016/7/21.
 * display news content
 */
public class ContentFragment extends BaseFragment implements ContentContract.View {
    private static final String TAG = "ContentFragment";
    private static final String NEWS_ID = "ContentFragment.news_id";
    @BindView(R.id.tv_content_title)
    TextView tvTitle;
    @BindView(R.id.tv_content_header)
    TextView tvHeader;
    @BindView(R.id.tv_content_summary)
    TextView tvSummary;
    @BindView(R.id.iv_content_thumb)
    ImageView ivThumb;
    @BindView(R.id.wv_content_detail)
    WebView wvDetail;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @Inject
    ContentPresenter mPresenter;
    @Inject
    NetworkUtil mNetworkUtil;
    @Inject
    JsInterface mJsInterface;
    private Content mContent;
    private String mSid;

    public static ContentFragment newInstance(String sid) {
        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putString(NEWS_ID, sid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSid = getArguments().getString(NEWS_ID);
        setHasOptionsMenu(true);
        inject(((BaseActivity) getActivity()).getActivityComponent());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_content, container, false);
        ButterKnife.bind(this, view);
        setupWebView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.setView(this);
        if (savedInstanceState == null) {
            mPresenter.retrieveContent(1, mSid);
        }
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void setupWebView() {
        WebSettings webSettings = wvDetail.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        if (Build.VERSION.SDK_INT >= 19) {
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        }
        //new added
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setAllowFileAccess(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(getActivity().getCacheDir().getAbsolutePath());
        if (mNetworkUtil.isNetworkOn()) {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        wvDetail.setWebViewClient(new WebViewClient());
        wvDetail.setWebChromeClient(new WebChromeClient());
        wvDetail.addJavascriptInterface(mJsInterface, "Android");

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_content, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_content_share:
                MessageUtil.toast("share", getActivity());
                return true;
            case R.id.menu_content_bookmark:
                MessageUtil.toast("bookmark", getActivity());
                return true;
            case R.id.menu_content_open_mobile:
                Navigator.toBrowser(mContent.getSid(), true, getActivity());
                return true;
            case R.id.menu_content_open_pc:
                Navigator.toBrowser(mContent.getSid(), false, getActivity());
                return true;
        }
        return false;
    }

    @Override
    protected void inject(ActivityComponent component) {
        component.fragmentComponent(new FragmentModule(this)).inject(this);
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
        return getContext();
    }

    @Override
    public void renderContent(final Content content) {
        mContent = content;
        String html = HtmlUtil.wrapContent(content.getDetail());
        tvTitle.setText(content.getTitle());
        String header = String.format(getActivity().getResources().getString(R.string.ph_news_content_header),
                DateUtil.toLongDatePlusTime(content.getTime(), getActivity()), content.getSource());
        tvHeader.setText(header);
        tvSummary.setText(content.getSummary());
        //thumb
        Glide.with(getActivity()).load(content.getThumb()).into(ivThumb);
        ivThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        //detail
        wvDetail.loadDataWithBaseURL(ApiUtil.BASE_URL, html, "text/html", "utf-8", null);
    }

    @Override
    public void onLoadFail(int infoId) {
        MessageUtil.toast(infoId, getContext());
    }

}
