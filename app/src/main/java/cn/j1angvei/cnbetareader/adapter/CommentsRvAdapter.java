package cn.j1angvei.cnbetareader.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.bean.CommentAction;
import cn.j1angvei.cnbetareader.bean.CommentItem;
import cn.j1angvei.cnbetareader.contract.CommentsContract;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.util.DateUtil;
import cn.j1angvei.cnbetareader.util.Navigator;

/**
 * Created by Wayne on 2016/7/28.
 * O means original comment, R means referenced comment.
 */
@PerFragment
public class CommentsRvAdapter extends RecyclerView.Adapter<CommentsRvAdapter.ViewHolder> implements BaseAdapter<List<CommentItem>> {
    private static List<CommentItem> mCommentItems;
    private static CommentsContract.View mView;

    @Inject
    public CommentsRvAdapter(Activity activity) {
        if (activity instanceof CommentsContract.View) {
            mView = (CommentsContract.View) activity;
        } else {
            throw new ClassCastException(activity.toString() + "must implement  CommentsContract.View");
        }
        mCommentItems = new ArrayList<>();
    }

    private static void showPopupMenu(final CommentItem item, final View view) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_popup_comment, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_comment_reply:
                        Navigator.toPublishComment(false, item.getContent(), item.getSid(), item.getTid(), view.getContext());
                        return true;
                    case R.id.action_comment_report:
                        mView.prepareJudgeComment(CommentAction.REPORT.toString(), item.getTid());
                        return true;
                }
                return false;
            }
        });
        popup.show();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_all, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final CommentItem item = mCommentItems.get(position);
        Context context = holder.itemView.getContext();
        holder.cvgOrigin.setComment(item, context);
        //if has no reference comment aka pid==0, set it invisible
        String pid = item.getPid();
        if (!TextUtils.equals(pid, "0")) {
            CommentItem itemReference = null;
            for (CommentItem commentItem : mCommentItems) {
                if (TextUtils.equals(commentItem.getTid(), pid)) {
                    itemReference = commentItem;
                }
            }
            if (itemReference != null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                @SuppressLint("InflateParams")
                View viewReference = inflater.inflate(R.layout.include_comment_ref, null);
                holder.cvgReference.findViews(viewReference);
                holder.cvgReference.setComment(itemReference, holder.itemView.getContext());
                ViewGroup parentView = (ViewGroup) holder.itemView.findViewById(R.id.insert_point_ref);
                parentView.addView(viewReference, 0, new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                holder.setIsRecyclable(false);
            }
        }
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
        CommentViewGroup cvgOrigin;
        CommentViewGroup cvgReference;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            cvgOrigin = new CommentViewGroup();
            cvgOrigin.findViews(itemView);
            cvgReference = new CommentViewGroup();
        }
    }

    private static class CommentViewGroup {
        private TextView tvUser;
        private TextView tvDate;
        private TextView tvContent;
        private TextView tvSupport;
        private TextView tvAgainst;
        private TextView tvPopup;
        private ImageView ivPhoto;

        public void findViews(View view) {
            tvUser = (TextView) view.findViewById(R.id.tv_comment_user);
            tvDate = (TextView) view.findViewById(R.id.tv_comment_date);
            tvContent = (TextView) view.findViewById(R.id.tv_comment_content);
            tvSupport = (TextView) view.findViewById(R.id.tv_comment_support);
            tvAgainst = (TextView) view.findViewById(R.id.tv_comment_against);
            tvPopup = (TextView) view.findViewById(R.id.tv_comment_popup);
            ivPhoto = (ImageView) view.findViewById(R.id.iv_comment_photo);
        }

        public void setComment(final CommentItem item, Context context) {
            Resources resources = context.getResources();
            String user = String.format(resources.getString(R.string.cmt_header), item.getUsername(), item.getLocation());
            tvUser.setText(user);
            String date = String.format(resources.getString(R.string.cmt_time), DateUtil.toTime(item.getDate(), context));
            tvDate.setText(date);
            tvContent.setText(item.getContent());
            String support = String.format(resources.getString(R.string.cmt_action_up_vote), item.getSupport());
            tvSupport.setText(support);
            tvSupport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //operate support comment
                    mView.prepareJudgeComment(CommentAction.SUPPORT.toString(), item.getTid());
                }
            });
            tvPopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(item, view);
                }
            });
            String against = String.format(resources.getString(R.string.cmt_action_down_vote), item.getAgainst());
            tvAgainst.setText(against);
            tvAgainst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //operate against comment
                    mView.prepareJudgeComment(CommentAction.AGAINST.toString(), item.getTid());
                }
            });
            if (!TextUtils.isEmpty(item.getHeadPhoto())) {
                Glide.with(context).load(item.getHeadPhoto()).into(ivPhoto);
            }
        }
    }

}
