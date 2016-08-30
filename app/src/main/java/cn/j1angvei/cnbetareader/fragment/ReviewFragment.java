package cn.j1angvei.cnbetareader.fragment;


import cn.j1angvei.cnbetareader.adapter.ReviewRvAdapter;
import cn.j1angvei.cnbetareader.bean.Review;
import cn.j1angvei.cnbetareader.di.component.ActivityComponent;
import cn.j1angvei.cnbetareader.di.module.FragmentModule;
import cn.j1angvei.cnbetareader.di.module.sub.ReviewModule;

/**
 * Created by Wayne on 2016/7/5.
 */
public class ReviewFragment extends NewsFragment<Review, ReviewRvAdapter.ViewHolder> {

    public static ReviewFragment newInstance(String type) {
        ReviewFragment fragment = new ReviewFragment();
        fragment.setBundle(type);
        return fragment;
    }

    @Override
    protected void inject(ActivityComponent component) {
        component.reviewComponent(new ReviewModule(),new FragmentModule(this)).inject(this);
    }
}
