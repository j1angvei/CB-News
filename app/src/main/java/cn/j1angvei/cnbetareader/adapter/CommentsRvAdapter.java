package cn.j1angvei.cnbetareader.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
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
    private static final String TAG = "CommentsRvAdapter";
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
        Log.d(TAG, "onCreateViewHolder: ");
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + holder.toString() + " pos " + position);
        final CommentItem item = mCommentItems.get(position);
        Context context = holder.itemView.getContext();
        holder.cvgOrigin.setComment(item, context, holder.getAdapterPosition());
        //if has no reference comment aka pid==0, set it invisible
        String idR = item.getPid();
        if (!idR.equals("0")) {
            CommentItem itemReference = null;
            int positionReference = 0;
            for (int i = 0; i < mCommentItems.size(); i++) {
                CommentItem tmpItem = mCommentItems.get(i);
                if (tmpItem.getTid().equals(idR)) {
                    itemReference = tmpItem;
                    positionReference = i;
                    break;
                }
            }
            if (itemReference != null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewReference = inflater.inflate(R.layout.include_comment_ref, null);
                holder.cvgReference.findViews(viewReference);
                holder.cvgReference.setComment(itemReference, holder.itemView.getContext(), positionReference);
                ViewGroup parentView = (ViewGroup) holder.itemView.findViewById(R.id.insert_point_ref);
                parentView.addView(viewReference, 0, new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                holder.setIsRecyclable(false);
            }
        }
    }


    private static void showPopupMenu(final int position, final View view) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_popup_comment, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_comment_reply:
                        MessageUtil.toast("reply comment" + position, view.getContext());
                        return true;
                    case R.id.action_comment_report:
                        MessageUtil.toast("report comment" + position, view.getContext());
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
        CommentViewGroup cvgOrigin;
        CommentViewGroup cvgReference;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "ViewHolder: " + isRecyclable() + "  " + toString());
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

        public void setComment(CommentItem item, Context context, final int position) {
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
                }
            });
            tvPopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(position, view);
                }
            });
            String against = String.format(resources.getString(R.string.cmt_action_down_vote), item.getAgainst());
            tvAgainst.setText(against);
            tvAgainst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //operate against comment
                }
            });
            if (!TextUtils.isEmpty(item.getHeadPhoto())) {
                Glide.with(context).load(item.getHeadPhoto()).into(ivPhoto);
            }
        }
    }

}
