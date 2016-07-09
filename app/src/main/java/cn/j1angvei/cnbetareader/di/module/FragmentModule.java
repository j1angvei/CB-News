package cn.j1angvei.cnbetareader.di.module;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;

import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.adapter.ReviewRvAdapter;
import cn.j1angvei.cnbetareader.adapter.ArticlesRvAdapter;
import cn.j1angvei.cnbetareader.adapter.HeadlineRvAdapter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Wayne on 2016/6/16.
 */
@Module
public class FragmentModule {

    @Provides
    @PerFragment
    LinearLayoutManager provideLinearLayoutManager(Activity activity) {
        return new LinearLayoutManager(activity);
    }

    @Provides
    @PerFragment
    ArticlesRvAdapter provideArticleRxAdapter(Activity activity) {
        return new ArticlesRvAdapter(activity);
    }

    @Provides
    @PerFragment
    ReviewRvAdapter provideReviewRxAdapter(Activity activity) {
        return new ReviewRvAdapter(activity);
    }

    @Provides
    @PerFragment
    HeadlineRvAdapter provideHeadlineRvAdapter(Activity activity) {
        return new HeadlineRvAdapter(activity);
    }

}
