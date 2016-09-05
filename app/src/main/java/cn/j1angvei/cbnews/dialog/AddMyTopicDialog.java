package cn.j1angvei.cbnews.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cbnews.R;
import cn.j1angvei.cbnews.activity.BaseActivity;
import cn.j1angvei.cbnews.adapter.AddMyTopicAdapter;
import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.contract.AddMyTopicContract;
import cn.j1angvei.cbnews.contract.MyTopicsContract;
import cn.j1angvei.cbnews.di.component.ActivityComponent;
import cn.j1angvei.cbnews.di.module.FragmentModule;
import cn.j1angvei.cbnews.presenter.AddTopicPresenter;
import cn.j1angvei.cbnews.util.MessageUtil;

/**
 * Created by Wayne on 2016/8/28.
 * open a dialog to add Topic
 */

public class AddMyTopicDialog extends DialogFragment implements BaseDialog, AddMyTopicContract.View {
    private static final String TAG = "AddMyTopicDialog";
    @BindView(R.id.list_view_expand)
    ExpandableListView mListView;
    AddMyTopicAdapter mAdapter;
    @Inject
    AddTopicPresenter mPresenter;
    private Context mContext;

    @Override
    public void inject(ActivityComponent component) {
        component.fragmentComponent(new FragmentModule(this)).inject(this);
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
                    mPresenter.retrieveTopics(groupPosition + 1);
                }
            }
        });
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
                .setNegativeButton(R.string.action_cancel, null)
                .create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button save = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<Topic> topics = mAdapter.getSelectedItems();
                        Log.d(TAG, "onClick: " + topics);
                        mPresenter.addToMyTopics(topics);
                    }
                });
                Button cancel = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                        mAdapter.clearSelectedItems();
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
        ((MyTopicsContract.View) getTargetFragment()).refreshMyTopics();
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }
}
