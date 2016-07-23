package cn.j1angvei.cnbetareader.di.module;

import android.app.Activity;

import cn.j1angvei.cnbetareader.adapter.ArticlesRvAdapter;
import cn.j1angvei.cnbetareader.adapter.SwipeAdapter;
import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.data.DataRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.presenter.NewsPresenter;
import cn.j1angvei.cnbetareader.presenter.MyTopicsPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Wayne on 2016/7/10.
 */
//@Module()
public class MyTopicsModule {
//    @Provides
//    @PerFragment
//    SwipeAdapter<Article, ArticlesRvAdapter.ViewHolder> provideTopicsRvAdapter(Activity activity) {
//        return new ArticlesRvAdapter(activity);
//    }
//
//    @Provides
//    @PerFragment
//    NewsPresenter<Article> topicsPresenter(DataRepository repository) {
//        return new MyTopicsPresenter(repository);
//    }
}
