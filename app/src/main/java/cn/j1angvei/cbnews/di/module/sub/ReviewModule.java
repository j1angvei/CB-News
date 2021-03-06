package cn.j1angvei.cbnews.di.module.sub;

import android.support.v4.app.Fragment;

import cn.j1angvei.cbnews.newslist.NewsAdapter;
import cn.j1angvei.cbnews.newslist.review.ReviewRvAdapter;
import cn.j1angvei.cbnews.bean.Review;
import cn.j1angvei.cbnews.base.Repository;
import cn.j1angvei.cbnews.di.qualifier.QReview;
import cn.j1angvei.cbnews.di.scope.PerFragment;
import cn.j1angvei.cbnews.newslist.NewsPresenter;
import dagger.Module;
import dagger.Provides;


/**
 * Created by Wayne on 2016/7/22.
 */
@Module
public class ReviewModule {
    @Provides
    @PerFragment
    NewsAdapter<Review, ReviewRvAdapter.ViewHolder> provideReviewRvAdapter(Fragment fragment) {
        return new ReviewRvAdapter(fragment);
    }

    @Provides
    @PerFragment
    NewsPresenter<Review> reviewsPresenter(@QReview Repository<Review> repository) {
        return new NewsPresenter<>(repository);
    }

}
