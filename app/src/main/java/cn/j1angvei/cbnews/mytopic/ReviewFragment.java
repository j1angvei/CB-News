package cn.j1angvei.cbnews.mytopic;


import cn.j1angvei.cbnews.newslist.NewsFragment;
import cn.j1angvei.cbnews.hotcoment.ReviewRvAdapter;
import cn.j1angvei.cbnews.bean.Review;
import cn.j1angvei.cbnews.di.component.ActivityComponent;
import cn.j1angvei.cbnews.di.module.FragmentModule;
import cn.j1angvei.cbnews.di.module.sub.ReviewModule;

/**
 * Created by Wayne on 2016/7/5.
 * display hot comment(aka review)
 */
public class ReviewFragment extends NewsFragment<Review, ReviewRvAdapter.ViewHolder> {

    public static ReviewFragment newInstance(String type) {
        ReviewFragment fragment = new ReviewFragment();
        fragment.setBundle(type);
        return fragment;
    }

    @Override
    protected void inject(ActivityComponent component) {
        component.reviewComponent(new ReviewModule(), new FragmentModule(this)).inject(this);
    }
}
