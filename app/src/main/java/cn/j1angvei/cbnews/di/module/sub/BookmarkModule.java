package cn.j1angvei.cbnews.di.module.sub;

import android.support.v4.app.Fragment;

import javax.inject.Named;

import cn.j1angvei.cbnews.adapter.BookmarkRvAdapter;
import cn.j1angvei.cbnews.adapter.NewsAdapter;
import cn.j1angvei.cbnews.bean.Bookmark;
import cn.j1angvei.cbnews.data.repository.NewsRepository;
import cn.j1angvei.cbnews.di.scope.PerFragment;
import cn.j1angvei.cbnews.presenter.NewsPresenter;
import dagger.Module;
import dagger.Provides;

import static cn.j1angvei.cbnews.bean.News.Type.BOOKMARK;

/**
 * Created by Wayne on 2016/7/26.
 */
@Module
public class BookmarkModule {
    @Provides
    @PerFragment
    NewsAdapter<Bookmark, BookmarkRvAdapter.ViewHolder> provideBookmarkRvAdapter(Fragment fragment) {
        return new BookmarkRvAdapter(fragment);
    }

    @Provides
    @PerFragment
    NewsPresenter<Bookmark> bookmarkPresenter(@Named(BOOKMARK) NewsRepository<Bookmark> repository) {
        return new NewsPresenter<>(repository);
    }
}
