package cn.j1angvei.cnbetareader.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.activity.BaseActivity;
import cn.j1angvei.cnbetareader.adapter.AddMyTopicAdapter;
import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.contract.AddMyTopicContract;
import cn.j1angvei.cnbetareader.di.component.ActivityComponent;
import cn.j1angvei.cnbetareader.di.module.FragmentModule;
import cn.j1angvei.cnbetareader.listener.DefaultMultiChoiceModeListener;
import cn.j1angvei.cnbetareader.presenter.AddTopicPresenter;
import cn.j1angvei.cnbetareader.util.MessageUtil;

/**
 * Created by Wayne on 2016/8/28.
 */

public class AddMyTopicDialog extends DialogFragment implements BaseDialog, AddMyTopicContract.View {
    private static final String TAG = "AddMyTopicDialog";
    public static final String ADD_TOPIC = "add_topic";
    @BindView(R.id.list_view_expand)
    ExpandableListView mListView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    AddMyTopicAdapter mAdapter;
    @Inject
    AddTopicPresenter mPresenter;
    private Context mContext;

    @Override
    public void inject(ActivityComponent component) {
        component.fragmentComponent(new FragmentModule()).inject(this);
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
        mPresenter.setView(this);
        //expandableListView
        mAdapter = new AddMyTopicAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int size = mAdapter.getChildrenCount(groupPosition);
                if (size == 0) {
                    mPresenter.retrieveTopics(groupPosition);
                }
            }
        });
        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (mListView.getChoiceMode() == ExpandableListView.CHOICE_MODE_MULTIPLE_MODAL) {
                    int index = mListView.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
                    mListView.setItemChecked(index, true);
                }
                return true;
            }
        });
        mListView.setChoiceMode(ExpandableListView.CHOICE_MODE_MULTIPLE_MODAL);
        mListView.setMultiChoiceModeListener(new DefaultMultiChoiceModeListener());
    }

    private List<Topic> getSelectedItems() {
        List<Topic> topics = new ArrayList<>();
        SparseBooleanArray array = mListView.getCheckedItemPositions();
        for (int i = 0; i < mListView.getCount(); i++) {
            if (array.get(i)) {
                Topic topic = (Topic) mListView.getItemAtPosition(i);
                topics.add(topic);
            }
        }
        return topics;
    }

    private void clearSelectedItems() {
        SparseBooleanArray array = mListView.getCheckedItemPositions();
        for (int i = 0; i < mListView.getCount(); i++) {
            if (array.get(i)) {
                mListView.setItemChecked(i, false);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject(((BaseActivity) getActivity()).getActivityComponent());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_topic, null);
        initView(view);
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.title_add_my_topic)
                .setView(view)
                .setPositiveButton(R.string.action_add, null)
                .setNeutralButton(R.string.action_reset, null)
                .setNegativeButton(R.string.action_drop, null)
                .create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button save = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.addToMyTopics(getSelectedItems());
                    }
                });
                Button reset = alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);
                reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clearSelectedItems();
                    }
                });
            }
        });
        return alertDialog;
    }

    @Override
    public Context getViewContext() {
        return mContext;
    }

    @Override
    public void renderTopics(int groupPosition, List<Topic> topics) {
        mAdapter.addItem(groupPosition, topics);
    }

    @Override
    public void onAddMyTopicsSuccess() {
        dismiss();
        MessageUtil.toast(R.string.info_add_my_topic_success, mContext);
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }
}
