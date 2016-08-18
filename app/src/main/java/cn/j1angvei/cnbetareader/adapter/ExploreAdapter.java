package cn.j1angvei.cnbetareader.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;

/**
 * Created by Wayne on 2016/8/16.
 */
@PerFragment
public class ExploreAdapter extends android.widget.BaseAdapter implements BaseAdapter<Topic> {
    private final Activity mActivity;
    private final List<Topic> mTopics;

    @Inject
    public ExploreAdapter(Activity activity) {
        mActivity = activity;
        mTopics = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mTopics.size();
    }

    @Override
    public Object getItem(int i) {
        return mTopics.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.item_topic, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Topic topic = mTopics.get(position);
        Glide.with(mActivity).load(topic.getCover()).into(viewHolder.ivThumb);
        viewHolder.tvName.setText(topic.getTitle());
        return convertView;
    }

    @Override
    public void clear() {
        mTopics.clear();
        notifyDataSetChanged();
    }

    @Override
    public void add(Topic item) {
        mTopics.add(item);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        ImageView ivThumb;
        TextView tvName;

        public ViewHolder(View view) {
            ivThumb = (ImageView) view.findViewById(R.id.iv_topic_thumb);
            tvName = (TextView) view.findViewById(R.id.tv_topic_name);
        }
    }
}
