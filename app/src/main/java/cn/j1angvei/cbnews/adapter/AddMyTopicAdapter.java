package cn.j1angvei.cbnews.adapter;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.j1angvei.cbnews.R;
import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.contract.AddMyTopicContract;
import cn.j1angvei.cbnews.di.scope.PerFragment;
import cn.j1angvei.cbnews.util.StringUtil;

/**
 * Created by Wayne on 2016/8/28.
 * adapter to get All Topic for user to select
 */
@PerFragment
public class AddMyTopicAdapter extends BaseExpandableListAdapter implements BaseExpandAdapter<Topic> {
    private static final int GROUP_SIZE = 26;
    private final SparseArray<List<Topic>> mTopicArray;
    private final AddMyTopicContract.View mView;
    private final List<Topic> mSelected;

    @Inject
    public AddMyTopicAdapter(AddMyTopicContract.View view) {
        mView = view;
        mTopicArray = new SparseArray<>();
        for (int i = 0; i < GROUP_SIZE; i++) {
            mTopicArray.put(i, new ArrayList<Topic>());
        }
        mSelected = new ArrayList<>();
    }

    @Override
    public int getGroupCount() {
        return GROUP_SIZE;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mTopicArray.get(groupPosition, new ArrayList<Topic>()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mTopicArray.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mView.getViewContext(), R.layout.item_topic_group, null);
            holder = new GroupHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        holder.tvLetter.setText(StringUtil.intToAlphabetLetter(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mView.getViewContext(), R.layout.item_topic_horz, null);
            holder = new ChildHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }
        final Topic topic = (Topic) getChild(groupPosition, childPosition);
        holder.tvTitle.setText(topic.getTitle());
        holder.cbSelected.setChecked(mSelected.contains(topic));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelected.contains(topic)) {
                    holder.cbSelected.setChecked(false);
                    mSelected.remove(topic);
                } else {
                    holder.cbSelected.setChecked(true);
                    mSelected.add(topic);
                }
            }
        });
        Glide.with(convertView.getContext()).load(topic.getThumb()).into(holder.ivThumb);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void addItem(int groupPosition, List<Topic> items) {
        mTopicArray.setValueAt(groupPosition, items);
        notifyDataSetChanged();
    }

    @Override
    public List<Topic> getSelectedItems() {
        return mSelected;
    }

    @Override
    public void clearSelectedItems() {
        mSelected.clear();
    }

    private static class GroupHolder {
        TextView tvLetter;
        ProgressBar pb;
        View itemView;

        GroupHolder(View view) {
            itemView = view;
            tvLetter = (TextView) view.findViewById(R.id.tv_add_topic_letter);
            pb = (ProgressBar) view.findViewById(R.id.progress_bar);
        }
    }

    private static class ChildHolder {
        TextView tvTitle;
        ImageView ivThumb;
        CheckBox cbSelected;
        View itemView;

        ChildHolder(View view) {
            itemView = view;
            tvTitle = (TextView) view.findViewById(R.id.tv_add_topic_item_title);
            ivThumb = (ImageView) view.findViewById(R.id.iv_add_topic_item_thumb);
            cbSelected = (CheckBox) view.findViewById(R.id.cb_add_topic_selected);
        }
    }
}
