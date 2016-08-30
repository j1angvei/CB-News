package cn.j1angvei.cnbetareader.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.util.MessageUtil;

/**
 * Created by Wayne on 2016/8/16.
 * adapter display all Topics
 */
@PerFragment
public class AllTopicsRvAdapter extends RecyclerView.Adapter<AllTopicsRvAdapter.ViewHolder> implements BaseAdapter<Topic> {
    private final Activity mActivity;
    private final List<Topic> mTopics;

    @Inject
    public AllTopicsRvAdapter(Activity activity) {
        mActivity = activity;
        mTopics = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mActivity).inflate(R.layout.item_topic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Context context = holder.itemView.getContext();
        final Topic topic = mTopics.get(position);
        holder.tvName.setText(topic.getTitle());
        Glide.with(context).load(topic.getThumb()).into(holder.ivThumb);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageUtil.toast(topic.getId(), context);
            }
        });
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

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_topic_thumb)
        ImageView ivThumb;
        @BindView(R.id.tv_topic_name)
        TextView tvName;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this.itemView);
            ivThumb = (ImageView) itemView.findViewById(R.id.iv_topic_thumb);
            tvName = (TextView) itemView.findViewById(R.id.tv_topic_name);
        }

    }
}
