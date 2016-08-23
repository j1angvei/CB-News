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
import cn.j1angvei.cnbetareader.util.ApiUtil;
import dagger.Module;
import dagger.Provides;
import static cn.j1angvei.cnbetareader.bean.News.Type.BOOKMARK;

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
    NewsPresenter<Bookmark> bookmarkPresenter(@Named(BOOKMARK) NewsRepository<Bookmark> repository, ApiUtil apiUtil) {
        return new NewsPresenter<>(repository, apiUtil);
    }
}
