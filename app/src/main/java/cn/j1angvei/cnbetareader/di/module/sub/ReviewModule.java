package cn.j1angvei.cnbetareader.di.module.sub;

import android.support.v4.app.Fragment;

import javax.inject.Named;

import cn.j1angvei.cnbetareader.adapter.NewsAdapter;
import cn.j1angvei.cnbetareader.adapter.ReviewRvAdapter;
import cn.j1angvei.cnbetareader.bean.Review;
import cn.j1angvei.cnbetareader.data.repository.NewsRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.presenter.NewsPresenter;
import cn.j1angvei.cnbetareader.util.ApiUtil;
import dagger.Module;
import dagger.Provides;

import static cn.j1angvei.cnbetareader.bean.News.Type.REVIEW;


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
    NewsPresenter<Review> reviewsPresenter(@Named(REVIEW) NewsRepository<Review> repository, ApiUtil apiUtil) {
        return new NewsPresenter<>(repository, apiUtil);
    }

}
