package cn.j1angvei.cbnews.mybookmark;

import cn.j1angvei.cbnews.newslist.NewsFragment;
import cn.j1angvei.cbnews.bean.Bookmark;
import cn.j1angvei.cbnews.di.component.ActivityComponent;
import cn.j1angvei.cbnews.di.module.FragmentModule;
import cn.j1angvei.cbnews.di.module.sub.BookmarkModule;

/**
 * Created by Wayne on 2016/7/26.
 */
public class BookmarkFragment extends NewsFragment<Bookmark, BookmarkRvAdapter.ViewHolder> {
    public static BookmarkFragment newInstance(String type) {
        BookmarkFragment fragment = new BookmarkFragment();
        fragment.setBundle(type);
        return fragment;
    }

    @Override
    protected void inject(ActivityComponent component) {
        component.bookmarkComponent(new BookmarkModule(), new FragmentModule(this)).inject(this);
    }

}
