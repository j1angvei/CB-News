package cn.j1angvei.cnbetareader.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;

/**
 * Created by Wayne on 2016/8/16.
 */
@PerFragment
public class TopicAdapter extends ArrayAdapter<Topic> {
    private final Activity mActivity;

    @Inject
    public TopicAdapter(Activity activity) {
        super(activity, 0);
        mActivity = activity;
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
        Topic topic = getItem(position);
        if (topic != null) {
            Glide.with(mActivity).load(topic.getThumb()).into(viewHolder.ivThumb);
            viewHolder.tvName.setText(topic.getTitle());
        }
        return convertView;
    }

    static class ViewHolder {
        ImageView ivThumb;
        TextView tvName;

        public ViewHolder(View view) {
            ivThumb = (ImageView) view.findViewById(R.id.iv_topic_thumb);
            tvName = (TextView) view.findViewById(R.id.tv_topic_name);
            tvName.setSelected(true);
        }
    }
}
