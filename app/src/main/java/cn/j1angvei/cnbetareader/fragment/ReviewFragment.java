package cn.j1angvei.cnbetareader.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import cn.j1angvei.cnbetareader.bean.Review;
import cn.j1angvei.cnbetareader.di.component.ActivityComponent;
import cn.j1angvei.cnbetareader.di.module.ReviewModule;
import cn.j1angvei.cnbetareader.adapter.ReviewRvAdapter;

/**
 * Created by Wayne on 2016/7/5.
 */
public class ReviewFragment extends SwipeFragment<Review, ReviewRvAdapter.ViewHolder> {

    public static ReviewFragment newInstance(String type) {
        ReviewFragment fragment = new ReviewFragment();
        fragment.setBundle(type);
        return fragment;
    }

    @Override
    protected void inject(ActivityComponent component) {
        component.reviewsComponent(new ReviewModule()).inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.setView(this);
        mPresenter.retrieveItem(mType, mPage++);
    }


    @Override
    public void renderItem(Review item) {
        mAdapter.add(item);
    }

    @Override
    public void clearItems() {
        mAdapter.clear();
    }

    @Override
    public void onRefresh() {

    }
}
