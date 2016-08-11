package cn.j1angvei.cnbetareader.fragment;

import android.view.View;

import cn.j1angvei.cnbetareader.adapter.BookmarkRvAdapter;
import cn.j1angvei.cnbetareader.bean.Bookmark;
import cn.j1angvei.cnbetareader.di.component.ActivityComponent;
import cn.j1angvei.cnbetareader.di.module.sub.BookmarkModule;
import cn.j1angvei.cnbetareader.util.MessageUtil;

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
        component.bookmarkComponent(new BookmarkModule()).inject(this);
    }

    @Override
    public void onClick(View view) {
        MessageUtil.toast("bookmark", getActivity());
    }
}
