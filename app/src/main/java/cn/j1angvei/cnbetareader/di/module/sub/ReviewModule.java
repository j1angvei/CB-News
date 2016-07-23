package cn.j1angvei.cnbetareader.di.module.sub;

import android.app.Activity;

import javax.inject.Named;

import cn.j1angvei.cnbetareader.adapter.ReviewRvAdapter;
import cn.j1angvei.cnbetareader.adapter.SwipeAdapter;
import cn.j1angvei.cnbetareader.bean.Review;
import cn.j1angvei.cnbetareader.data.repository.NewsRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.presenter.NewsPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Wayne on 2016/7/22.
 */
@Module
public class ReviewModule {

    @Provides
    @PerFragment
    SwipeAdapter<Review, ReviewRvAdapter.ViewHolder> provideReviewRvAdapter(Activity activity) {
        return new ReviewRvAdapter(activity);
    }

    @Provides
    @PerFragment
    NewsPresenter<Review> reviewsPresenter(@Named("d_review") NewsRepository<Review> repository) {
        return new NewsPresenter<>(repository);
    }

}
