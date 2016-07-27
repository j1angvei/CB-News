package cn.j1angvei.cnbetareader.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
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
import cn.j1angvei.cnbetareader.di.component.ActivityComponent;
import cn.j1angvei.cnbetareader.di.module.FragmentModule;
import cn.j1angvei.cnbetareader.presenter.ContentPresenter;
import cn.j1angvei.cnbetareader.util.DateUtil;
import cn.j1angvei.cnbetareader.util.MessageUtil;
import cn.j1angvei.cnbetareader.view.ContentView;

/**
 * Created by Wayne on 2016/7/21.
 */
public class ContentFragment extends BaseFragment implements ContentView {
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
        setHasOptionsMenu(true);
        mSid = getArguments().getString(NEWS_ID);
        inject(((BaseActivity) getActivity()).getActivityComponent());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_content, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_content, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_content_add_comment:
                MessageUtil.shortToast("add comment", getActivity());
                return true;
            case R.id.menu_content_all_comments:
                MessageUtil.shortToast("all comment", getActivity());
                return true;
            case R.id.menu_content_bookmark:
                MessageUtil.shortToast("bookmark", getActivity());
                return true;
            case R.id.menu_content_share:
                MessageUtil.shortToast("share", getActivity());
                return true;
        }
        return false;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.setView(this);
        mPresenter.retrieveContent(mSid);
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
    public void renderItem(Content item) {
        //title
        tvTitle.setText(item.getTitle());
        //header
        Resources resources = getActivity().getResources();
        String header = String.format(resources.getString(R.string.ph_news_content_header), DateUtil.toLongDatePlusTime(item.getDate(), getActivity()), item.getSource());
        tvHeader.setText(header);
        //summary
        tvSummary.setText(item.getSummary());
        //thumb
        Glide.with(getActivity()).load(item.getTopicPhoto()).into(ivThumb);
        //detail
        String detail = String.format(resources.getString(R.string.ph_news_content_detail), item.getDetail());
        wvDetail.loadData(detail, "text/html;charset=utf-8", "utf-8");
    }

    @Override
    public void clearItems() {
    }

    @Override
    protected void inject(ActivityComponent component) {
        component.fragmentComponent(new FragmentModule()).inject(this);
    }
}
