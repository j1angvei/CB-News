package cn.j1angvei.cnbetareader.di.module.sub;

import android.support.v4.app.Fragment;

import javax.inject.Named;

import cn.j1angvei.cnbetareader.adapter.BookmarkRvAdapter;
import cn.j1angvei.cnbetareader.adapter.NewsAdapter;
import cn.j1angvei.cnbetareader.bean.Bookmark;
import cn.j1angvei.cnbetareader.data.repository.NewsRepository;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.presenter.NewsPresenter;
import cn.j1angvei.cnbetareader.util.ApiUtil;
import dagger.Module;
import dagger.Provides;

import static cn.j1angvei.cnbetareader.bean.News.Type.BOOKMARK;

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
    NewsPresenter<Bookmark> bookmarkPresenter(@Named(BOOKMARK) NewsRepository<Bookmark> repository, ApiUtil apiUtil) {
        return new NewsPresenter<>(repository, apiUtil);
    }
}
