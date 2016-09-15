package cn.j1angvei.cbnews.di.component;

import cn.j1angvei.cbnews.di.module.FragmentModule;
import cn.j1angvei.cbnews.di.module.sub.BookmarkModule;
import cn.j1angvei.cbnews.di.scope.PerFragment;
import cn.j1angvei.cbnews.newslist.bookmark.BookmarkFragment;
import dagger.Subcomponent;

/**
 * Created by Wayne on 2016/7/26.
 * component specific for {@link BookmarkFragment}
 */
@PerFragment
@Subcomponent(modules = {BookmarkModule.class, FragmentModule.class})
public interface BookmarkComponent {
    void inject(BookmarkFragment fragment);
}
