package cn.j1angvei.cnbetareader.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.util.ToastUtil;

/**
 * Created by Wayne on 2016/7/13.
 */
public class ExploreRvAdapter extends SwipeAdapter<Topic, ExploreRvAdapter.ViewHolder> {
    private List<Topic> mTopics;
    private Activity mActivity;

    public ExploreRvAdapter(Activity activity) {
        mActivity = activity;
        mTopics = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExploreRvAdapter.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        final Topic topic = mTopics.get(position);
        Glide.with(context).load(topic.getCover()).into(holder.ivThumb);
        holder.ivThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showShortToast("to topic in future", mActivity);
            }
        });
        holder.tvName.setText(topic.getTitle());
//        holder.ivDotMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PopupMenu popupMenu = new PopupMenu(mActivity, view);
//                final MenuInflater menuInflater = popupMenu.getMenuInflater();
//                menuInflater.inflate(R.menu.menu_topic_popup, popupMenu.getMenu());
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.action_topic_add_to_my_topics:
//                                ToastUtil.showShortToast("add to my topic", mActivity);
//                                return true;
//                            case R.id.action_topic_open_topic_articles:
//                                ToastUtil.showShortToast("open topic articles", mActivity);
//                                return true;
//                        }
//                        return false;
//                    }
//                });
//                popupMenu.show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mTopics.size();
    }

    @Override
    public void clear() {
        int size = mTopics.size();
        mTopics.clear();
        notifyItemRangeRemoved(0, size);
    }

    @Override
    public void add(Topic item) {
        mTopics.add(item);
        notifyItemInserted(mTopics.size() - 1);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_topic_thumb)
        ImageView ivThumb;
        @BindView(R.id.tv_topic_name)
        TextView tvName;
//        @BindView(R.id.iv_topic_dot_menu)
//        ImageView ivDotMenu;

        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
