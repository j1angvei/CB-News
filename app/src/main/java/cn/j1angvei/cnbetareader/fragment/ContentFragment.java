package cn.j1angvei.cnbetareader.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.j1angvei.cnbetareader.bean.Content;
import cn.j1angvei.cnbetareader.di.component.ActivityComponent;
import cn.j1angvei.cnbetareader.view.BaseView;

/**
 * Created by Wayne on 2016/7/21.
 */
public class ContentFragment extends BaseFragment implements BaseView<Content> {
    private static final String NEWS_ID = "ContentFragment.news_id";

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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void renderItem(Content item) {

    }

    @Override
    public void clearItems() {

    }

    @Override
    protected void inject(ActivityComponent component) {

    }
}
