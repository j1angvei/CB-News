package cn.j1angvei.cnbetareader.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
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
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.activity.BaseActivity;
import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.contract.ContentContract;
import cn.j1angvei.cnbetareader.di.component.ActivityComponent;
import cn.j1angvei.cnbetareader.di.module.FragmentModule;
import cn.j1angvei.cnbetareader.presenter.ContentPresenter;
import cn.j1angvei.cnbetareader.util.DateUtil;
import cn.j1angvei.cnbetareader.util.MessageUtil;
import cn.j1angvei.cnbetareader.util.Navigator;

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
    CoordinatorLayout mCoordinatorLayout;
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
        mCoordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinator_layout);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.setView(this);
        if (savedInstanceState == null) {
            mPresenter.retrieveContent(mSid);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView() {
        //need to implement WebViewClient later to deal with link in WebView
        wvDetail.setWebViewClient(new WebViewClient());
        wvDetail.setWebChromeClient(new WebChromeClient());
        WebSettings settings = wvDetail.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        if (Build.VERSION.SDK_INT >= 19) {
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        }
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
    public void renderContent(Content content) {
        mContent = content;
        tvTitle.setText(content.getTitle());
        String header = String.format(getActivity().getResources().getString(R.string.ph_news_content_header),
                DateUtil.toLongDatePlusTime(content.getTime(), getActivity()), content.getSource());
        tvHeader.setText(header);
        tvSummary.setText(content.getSummary());
        //thumb
        Glide.with(getActivity()).load(content.getThumb()).into(ivThumb);
        //detail
        wvDetail.loadData(content.getDetail(), "text/html;charset=utf-8", "utf-8");
    }

    @Override
    public void onLoadFail(int infoId) {
        MessageUtil.snack(mCoordinatorLayout, infoId);
    }

}
