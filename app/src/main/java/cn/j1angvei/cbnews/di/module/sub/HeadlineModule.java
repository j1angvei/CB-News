package cn.j1angvei.cbnews.di.module.sub;

import android.support.v4.app.Fragment;

import cn.j1angvei.cbnews.pastheadline.HeadlineRvAdapter;
import cn.j1angvei.cbnews.newslist.NewsAdapter;
import cn.j1angvei.cbnews.bean.Headline;
import cn.j1angvei.cbnews.base.Repository;
import cn.j1angvei.cbnews.di.qualifier.QHeadline;
import cn.j1angvei.cbnews.di.scope.PerFragment;
import cn.j1angvei.cbnews.newslist.NewsPresenter;
import dagger.Module;
import dagger.Provides;


/**
 * Created by Wayne on 2016/7/22.
 */
@Module
public class HeadlineModule {
    @Provides
    @PerFragment
    NewsAdapter<Headline, HeadlineRvAdapter.ViewHolder> provideHeadlineRvAdapter(Fragment fragment) {
        return new HeadlineRvAdapter(fragment);
    }

    @Provides
    @PerFragment
    NewsPresenter<Headline> headlinesPresenter(@QHeadline Repository<Headline> repository) {
        return new NewsPresenter<>(repository);
    }
}
