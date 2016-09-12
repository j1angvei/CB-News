package cn.j1angvei.cbnews.mybookmark;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cbnews.R;
import cn.j1angvei.cbnews.bean.Bookmark;
import cn.j1angvei.cbnews.newslist.NewsContract;
import cn.j1angvei.cbnews.newslist.NewsAdapter;
import cn.j1angvei.cbnews.util.DateUtil;
import cn.j1angvei.cbnews.util.Navigator;

/**
 * Created by Wayne on 2016/7/26.
 * Adapter displaying user bookmarked news
 */
public class BookmarkRvAdapter extends NewsAdapter<Bookmark, BookmarkRvAdapter.ViewHolder> {
    private final ArrayList<Bookmark> mBookmarks;
    private NewsContract.View mView;

    public BookmarkRvAdapter(Fragment fragment) {
        mView = (NewsContract.View) fragment;
        mBookmarks = new ArrayList<>();
    }

    @Override
    public ArrayList<String> getSids() {
        ArrayList<String> sids = new ArrayList<>();
        for (Bookmark bookmark : mBookmarks) {
            sids.add(bookmark.getSid());
        }
        return sids;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookmark, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Bookmark bookmark = mBookmarks.get(position);
        holder.tvTitle.setText(bookmark.getTitle());
        Context context = holder.itemView.getContext();
        Resources resources = context.getResources();
        String time = String.format(resources.getString(R.string.ph_bookmark_time), DateUtil.toLongDatePlusTime(bookmark.getTime(), context));
        holder.tvTime.setText(time);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigator.toContent(holder.getAdapterPosition(), mBookmarks, mView.getViewContext());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBookmarks.size();
    }

    @Override
    public void clear() {
        int size = mBookmarks.size();
        mBookmarks.clear();
        notifyItemRangeRemoved(0, size);
    }

    @Override
    public void add(Bookmark item) {
        mBookmarks.add(item);
        notifyItemInserted(mBookmarks.size() - 1);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_bookmark_title)
        TextView tvTitle;
        @BindView(R.id.tv_bookmark_time)
        TextView tvTime;

        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }
}
