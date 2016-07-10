package cn.j1angvei.cnbetareader.di.module;

import android.app.Activity;

import cn.j1angvei.cnbetareader.adapter.ReviewRvAdapter;
import cn.j1angvei.cnbetareader.adapter.SwipeAdapter;
import cn.j1angvei.cnbetareader.bean.Review;
import cn.j1angvei.cnbetareader.data.DataRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.presenter.ReviewPresenter;
import cn.j1angvei.cnbetareader.presenter.SwipePresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Wayne on 2016/7/10.
 */
@Module(includes = FragmentModule.class)
public class ReviewModule {
    @Provides
    @PerFragment
    SwipeAdapter<Review, ReviewRvAdapter.ViewHolder> provideReviewRvAdapter(Activity activity) {
        return new ReviewRvAdapter(activity);
    }

    @Provides
    @PerFragment
    SwipePresenter<Review> reviewsPresenter(DataRepository repository) {
        return new ReviewPresenter(repository);
    }
}
