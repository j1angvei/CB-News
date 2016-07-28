package cn.j1angvei.cnbetareader.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.util.DateUtil;

/**
 * Created by Wayne on 2016/7/28.
 */
@PerFragment
public class CommentsRvAdapter extends RecyclerView.Adapter<CommentsRvAdapter.ViewHolder> implements BaseAdapter<List<CommentItem>> {
    private final Activity mActivity;
    private final List<CommentItem> mCommentItems;

    @Inject
    public CommentsRvAdapter(Activity activity) {
        mActivity = activity;
        mCommentItems = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CommentItem item = mCommentItems.get(position);
        Context context = holder.itemView.getContext();
        Resources resources = context.getResources();
        holder.llContainerRef.setVisibility(View.GONE);
        //header
        String header = String.format(resources.getString(R.string.cmt_header), item.getUsername(), item.getLocation());
        holder.tvHeaderOrg.setText(header);
        holder.tvTimeOrg.setText(DateUtil.toTime(item.getDate(), context));
        holder.tvContentOrg.setText(item.getContent());
        String upVoteOrg = String.format(resources.getString(R.string.cmt_action_up_vote), item.getUpVote());
        holder.btnUpVoteOrg.setText(upVoteOrg);
        String downVoteOrg = String.format(resources.getString(R.string.cmt_action_down_vote), item.getDownVote());
        holder.btnDownVoteOrg.setText(downVoteOrg);
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
        View inHeaderOrg;
        @BindView(R.id.iv_comment_photo)
        ImageView ivPhoto;
        @BindView(R.id.ll_comment_ref)
        LinearLayout llContainerRef;
        @BindView(R.id.in_comment_ref_header)
        View inHeaderRef;
        @BindView(R.id.tv_comment_ref_content)
        TextView tvContentRef;
        @BindView(R.id.in_comment_ref_action)
        View inActionRef;
        @BindView(R.id.tv_comment_org_content)
        TextView tvContentOrg;
        @BindView(R.id.in_org_comment_action)
        View inActionOrg;

        View itemView;
        //specific view in include tag
        TextView tvHeaderOrg;
        TextView tvHeaderRef;
        TextView tvTimeOrg;
        TextView tvTimeRef;
        Button btnUpVoteOrg;
        Button btnUpVoteRef;
        Button btnDownVoteOrg;
        Button btnDownVoteRef;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
            tvHeaderOrg = (TextView) inHeaderOrg.findViewById(R.id.tv_comment_user);
            tvHeaderRef = (TextView) inHeaderRef.findViewById(R.id.tv_comment_user);
            tvTimeOrg = (TextView) inHeaderOrg.findViewById(R.id.tv_comment_date);
            tvTimeRef = (TextView) inHeaderRef.findViewById(R.id.tv_comment_date);
            //four buttons of org comment
            btnUpVoteOrg = (Button) inActionOrg.findViewById(R.id.btn_comment_up_vote);
            btnDownVoteOrg = (Button) inActionOrg.findViewById(R.id.btn_comment_down_vote);
            //four buttons of ref comment
            btnUpVoteRef = (Button) inActionRef.findViewById(R.id.btn_comment_up_vote);
            btnDownVoteRef = (Button) inActionRef.findViewById(R.id.btn_comment_down_vote);
        }
    }
}
