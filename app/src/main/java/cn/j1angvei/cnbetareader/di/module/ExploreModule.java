package cn.j1angvei.cnbetareader.di.module;

import android.app.Activity;

import cn.j1angvei.cnbetareader.adapter.ExploreRvAdapter;
import cn.j1angvei.cnbetareader.adapter.SwipeAdapter;
import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.data.DataRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.presenter.TopicPresenter;
import cn.j1angvei.cnbetareader.presenter.NewsPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Wayne on 2016/7/15.
 */
//@Module()
public class ExploreModule {

//    @Provides
//    @PerFragment
//    SwipeAdapter<Topic, ExploreRvAdapter.ViewHolder> providesExploreRvAdapter(Activity activity) {
//        return new ExploreRvAdapter(activity);
//    }

//    @Provides
//    @PerFragment
//    NewsPresenter<Topic> explorePresenter(DataRepository repository) {
//        return new TopicPresenter(repository);
//    }
}
