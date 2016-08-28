package cn.j1angvei.cnbetareader.adapter;

import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.contract.AddTopicContract;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.util.StringUtil;

/**
 * Created by Wayne on 2016/8/28.
 */
@PerFragment
public class AddTopicExpandAdapter extends BaseExpandableListAdapter implements BaseExpandAdapter<Topic> {
    private static final int GROUP_SIZE = 26;
    private final SparseArray<List<Topic>> mTopicArray;
    private final AddTopicContract.View mView;

    @Inject
    public AddTopicExpandAdapter(AddTopicContract.View view) {
        mView = view;
        mTopicArray = new SparseArray<>();
        for (int i = 0; i < GROUP_SIZE; i++) {
            mTopicArray.put(i, new ArrayList<Topic>());
        }
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
        if (convertView == null) {
            convertView = View.inflate(mView.getViewContext(), R.layout.item_topic_group, null);
        }
        TextView tvHeader = (TextView) convertView.findViewById(R.id.tv_add_topic_group_letter);
        tvHeader.setText(StringUtil.indexToUpperLetter(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Topic topic = (Topic) getChild(groupPosition, childPosition);
        if (convertView == null) {
            convertView = View.inflate(mView.getViewContext(), R.layout.item_topic_horz, null);
        }
        ImageView ivThumb = (ImageView) convertView.findViewById(R.id.iv_add_topic_item_thumb);
        Picasso.with(mView.getViewContext()).load(topic.getThumb()).into(ivThumb);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_add_topic_item_title);
        tvTitle.setText(topic.getTitle());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void clearItems(int groupPosition) {
        mTopicArray.get(groupPosition).clear();
        notifyDataSetChanged();
    }

    @Override
    public void addItem(int groupPosition, List<Topic> items) {
        mTopicArray.setValueAt(groupPosition, items);
        notifyDataSetChanged();
    }

    @Override
    public List<Topic> getSelectedItems() {
        return null;
    }

    @Override
    public List<Topic> clearSelectedItems() {
        return null;
    }

}
