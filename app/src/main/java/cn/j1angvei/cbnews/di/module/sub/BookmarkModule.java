package cn.j1angvei.cbnews.di.module.sub;

import android.support.v4.app.Fragment;

import cn.j1angvei.cbnews.newslist.bookmark.BookmarkRvAdapter;
import cn.j1angvei.cbnews.newslist.NewsAdapter;
import cn.j1angvei.cbnews.bean.Bookmark;
import cn.j1angvei.cbnews.base.Repository;
import cn.j1angvei.cbnews.di.qualifier.QBookmark;
import cn.j1angvei.cbnews.di.scope.PerFragment;
import cn.j1angvei.cbnews.newslist.NewsPresenter;
import dagger.Module;
import dagger.Provides;

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
    NewsPresenter<Bookmark> bookmarkPresenter(@QBookmark Repository<Bookmark> repository) {
        return new NewsPresenter<>(repository);
    }
}
