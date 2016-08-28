package cn.j1angvei.cnbetareader.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.activity.BaseActivity;
import cn.j1angvei.cnbetareader.adapter.AddTopicExpandAdapter;
import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.contract.AddTopicContract;
import cn.j1angvei.cnbetareader.di.component.ActivityComponent;
import cn.j1angvei.cnbetareader.di.module.FragmentModule;
import cn.j1angvei.cnbetareader.presenter.AddTopicPresenter;

/**
 * Created by Wayne on 2016/8/28.
 */

public class AddTopicDialog extends BottomSheetDialogFragment implements BaseDialog, AddTopicContract.View {
    private static final String TAG = "AddTopicDialog";
    public static final String ADD_TOPIC = "add_topic";
    private BottomSheetBehavior mBehavior;
    @BindView(R.id.list_view_expand)
    ExpandableListView mListView;

    AddTopicExpandAdapter mAdapter;
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
        mAdapter = new AddTopicExpandAdapter(this);
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
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        mBehavior.setHideable(false);
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
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.dialog_add_topic, null);
        dialog.setContentView(view);
        initView(view);
        return dialog;
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
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
