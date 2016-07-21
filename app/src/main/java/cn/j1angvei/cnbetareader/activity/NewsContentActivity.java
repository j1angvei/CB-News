package cn.j1angvei.cnbetareader.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.List;

import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.util.ToastUtil;

/**
 * Created by Wayne on 2016/7/5.
 */
public class NewsContentActivity extends BaseActivity {
    public static final String NEWS_POSITION = "NewsContentActivity.news_position";
    public static final String NEWS_SIDS = "NewsContentActivity.news_source";
    private List<String> allSid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);
        allSid = getIntent().getStringArrayListExtra(NEWS_SIDS);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ToastUtil.showLongToast(allSid.toString(), this);
    }
}
