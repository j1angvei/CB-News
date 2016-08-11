package cn.j1angvei.cnbetareader.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.bean.CommentItem;
import cn.j1angvei.cnbetareader.contract.CommentsContract;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.util.DateUtil;
import cn.j1angvei.cnbetareader.util.MessageUtil;

/**
 * Created by Wayne on 2016/7/28.
 * O means original comment, R means referenced comment.
 */
@PerFragment
public class CommentsRvAdapter extends RecyclerView.Adapter<CommentsRvAdapter.ViewHolder> implements BaseAdapter<List<CommentItem>> {
    private final Activity mActivity;
    private final List<CommentItem> mCommentItems;
    private CommentsContract.View mView;

    @Inject
    public CommentsRvAdapter(Activity activity) {
        mActivity = activity;
        if (activity instanceof CommentsContract.View) {
            mView = (CommentsContract.View) activity;
        } else {
            throw new ClassCastException(activity.toString() + "must implement  CommentsContract.View");
        }
        mCommentItems = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final CommentItem item = mCommentItems.get(position);
        Context context = holder.itemView.getContext();
        Resources resources = context.getResources();
        //header
        String header = String.format(resources.getString(R.string.cmt_header), item.getUsername(), item.getLocation());
        holder.tvHeaderO.setText(header);
        String timeO = String.format(resources.getString(R.string.cmt_time), DateUtil.toTime(item.getDate(), context));
        holder.tvTimeO.setText(timeO);
        holder.tvContentO.setText(item.getContent());
        //button number color
        String supportO = String.format(resources.getString(R.string.cmt_action_up_vote), item.getUpVote());
        holder.btnSupportO.setText(supportO);
//        holder.btnSupportO.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mView.beforeOperateComment(CommentAction.SUPPORT.toString(), holder.getAdapterPosition());
//            }
//        });
        String againstO = String.format(resources.getString(R.string.cmt_action_down_vote), item.getDownVote());
        holder.btnAgainstO.setText(againstO);
//        holder.btnAgainstO.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mView.beforeOperateComment(CommentAction.AGAINST.toString(), holder.getAdapterPosition());
//            }
//        });
        holder.btnPopupO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.getAdapterPosition(), holder.btnPopupO);
            }
        });
        //if has no reference comment aka pid==0, set it invisible
        String idR = item.getReferenceId();
        if (idR.equals("0")) {
            holder.llContainerR.setVisibility(View.GONE);
        } else {
            CommentItem refItem = null;
            int refPosition = 0;
            for (int i = 0; i < mCommentItems.size(); i++) {
                CommentItem tmpItem = mCommentItems.get(i);
                if (tmpItem.getCommentId().equals(idR)) {
                    refItem = tmpItem;
                    refPosition = i;
                    break;
                }
            }
            if (refItem != null) {
                holder.llContainerR.setVisibility(View.VISIBLE);
                String headerR = String.format(resources.getString(R.string.cmt_header), refItem.getUsername(), refItem.getLocation());
                holder.tvHeaderR.setText(headerR);
                String timeR = String.format(resources.getString(R.string.cmt_time), DateUtil.toTime(refItem.getDate(), context));
                holder.tvTimeR.setText(timeR);
                holder.tvContentR.setText(refItem.getContent());
                String upVoteR = String.format(resources.getString(R.string.cmt_action_up_vote), refItem.getUpVote());
                holder.btnSupportR.setText(upVoteR);
                final int finalRefPosition = refPosition;
//                holder.btnSupportR.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        mView.beforeOperateComment(CommentAction.SUPPORT.toString(), finalRefPosition);
//                    }
//                });
                String downVoteR = String.format(resources.getString(R.string.cmt_action_down_vote), refItem.getDownVote());
                holder.btnAgainstR.setText(downVoteR);
//                holder.btnAgainstR.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        mView.beforeOperateComment(CommentAction.AGAINST.toString(), finalRefPosition);
//                    }
//                });
                holder.btnPopupR.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showPopupMenu(finalRefPosition, holder.btnPopupR);
                    }
                });
            }
        }
    }

    private void showPopupMenu(final int position, View view) {
        PopupMenu popup = new PopupMenu(mActivity, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_popup_comment, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_comment_reply:
                        MessageUtil.toast("reply comment", mActivity);
                        return true;
                    case R.id.action_comment_report:
//                        mView.beforeOperateComment(CommentAction.REPORT.toString(), position);
                        MessageUtil.toast("report comment", mActivity);
                        return true;
                }
                return false;
            }
        });
        popup.show();
    }

    @Override
    public int getItemCount() {
        return mCommentItems.size();
    }

    @Override
    public void clear() {
        int size = mCommentItems.size();
        mCommentItems.clear();
        notifyItemRangeRemoved(0, size);
    }

    @Override
    public void add(List<CommentItem> item) {
        clear();
        mCommentItems.addAll(item);
        notifyItemRangeInserted(0, item.size());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.in_comment_org_header)
        View inHeaderO;
        @BindView(R.id.iv_comment_photo)
        ImageView ivPhoto;
        @BindView(R.id.ll_comment_ref)
        LinearLayout llContainerR;
        @BindView(R.id.in_comment_ref_header)
        View inHeaderR;
        @BindView(R.id.tv_comment_ref_content)
        TextView tvContentR;
        @BindView(R.id.in_comment_ref_action)
        View inActionR;
        @BindView(R.id.tv_comment_org_content)
        TextView tvContentO;
        @BindView(R.id.in_org_comment_action)
        View inActionO;

        View itemView;
        //specific view in include tag
        TextView tvHeaderO;
        TextView tvHeaderR;
        TextView tvTimeO;
        TextView tvTimeR;
        Button btnSupportO;
        Button btnSupportR;
        Button btnPopupO;
        Button btnAgainstO;
        Button btnAgainstR;
        Button btnPopupR;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
            tvHeaderO = (TextView) inHeaderO.findViewById(R.id.tv_comment_user);
            tvHeaderR = (TextView) inHeaderR.findViewById(R.id.tv_comment_user);
            tvTimeO = (TextView) inHeaderO.findViewById(R.id.tv_comment_date);
            tvTimeR = (TextView) inHeaderR.findViewById(R.id.tv_comment_date);
            //four buttons of org comment
            btnSupportO = (Button) inActionO.findViewById(R.id.btn_comment_up_vote);
            btnAgainstO = (Button) inActionO.findViewById(R.id.btn_comment_down_vote);
            btnPopupO = (Button) inActionO.findViewById(R.id.btn_comment_popup);
            //four buttons of ref comment
            btnSupportR = (Button) inActionR.findViewById(R.id.btn_comment_up_vote);
            btnAgainstR = (Button) inActionR.findViewById(R.id.btn_comment_down_vote);
            btnPopupR = (Button) inActionR.findViewById(R.id.btn_comment_popup);
        }
    }

}
