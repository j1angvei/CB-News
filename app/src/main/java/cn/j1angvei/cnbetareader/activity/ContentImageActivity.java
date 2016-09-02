package cn.j1angvei.cnbetareader.activity;

/**
 * Created by Wayne on 2016/9/2.
 * show image in news content
 */

public class ContentImageActivity extends BaseActivity {
    public static final String CUR_POS = "ContentImageActivity.cur_pos";
    public static final String IMG_URLS = "ContentImageActivity.img_urls";
    private int mCurPos;
    private String[] mUrls;

    @Override
    protected void parseIntent() {
        mCurPos = getIntent().getIntExtra(CUR_POS, 0);
        mUrls = getIntent().getStringArrayExtra(IMG_URLS);
    }

    @Override
    protected void doInjection() {

    }

    @Override
    protected void initView() {

    }
}
