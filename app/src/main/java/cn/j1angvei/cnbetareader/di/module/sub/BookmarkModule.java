package cn.j1angvei.cnbetareader.di.module.sub;

import android.app.Activity;

import javax.inject.Named;

import cn.j1angvei.cnbetareader.adapter.BookmarkRvAdapter;
import cn.j1angvei.cnbetareader.adapter.NewsAdapter;
import cn.j1angvei.cnbetareader.bean.Bookmark;
import cn.j1angvei.cnbetareader.data.repository.NewsRepository;
import cn.j1angvei.cnbetareader.di.module.FragmentModule;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.presenter.NewsPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Wayne on 2016/7/26.
 */
@Module(includes = FragmentModule.class)
public class BookmarkModule {
    @Provides
    @PerFragment
    NewsAdapter<Bookmark, BookmarkRvAdapter.ViewHolder> provideBookmarkRvAdapter(Activity activity) {
        return new BookmarkRvAdapter(activity);
    }

    @Provides
    @PerFragment
    NewsPresenter<Bookmark> bookmarkPresenter(@Named("d_bookmark") NewsRepository<Bookmark> repository) {
        return new NewsPresenter<>(repository);
    }
}
