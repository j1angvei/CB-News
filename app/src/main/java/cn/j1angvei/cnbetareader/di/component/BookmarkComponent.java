package cn.j1angvei.cnbetareader.di.component;

import cn.j1angvei.cnbetareader.di.module.sub.BookmarkModule;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.fragment.BookmarkFragment;
import dagger.Subcomponent;

/**
 * Created by Wayne on 2016/7/26.
 */
@PerFragment
@Subcomponent(modules = BookmarkModule.class)
public interface BookmarkComponent {
    void inject(BookmarkFragment fragment);
}
