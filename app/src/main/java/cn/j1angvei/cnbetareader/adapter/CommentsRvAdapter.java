package cn.j1angvei.cnbetareader.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.FragmentManager;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.activity.CommentsActivity;
import cn.j1angvei.cnbetareader.bean.Action;
import cn.j1angvei.cnbetareader.bean.CommentItem;
import cn.j1angvei.cnbetareader.bean.Comments;
import cn.j1angvei.cnbetareader.contract.ShowCmtContract;
import cn.j1angvei.cnbetareader.di.scope.PerFragment;
import cn.j1angvei.cnbetareader.util.DateUtil;
import cn.j1angvei.cnbetareader.util.Navigator;

/**
 * Created by Wayne on 2016/7/28.
 * O means original comment, R means referenced comment.
 */
@PerFragment
public class CommentsRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements BaseAdapter<Comments> {
    private static final String TAG = "CommentsRvAdapter";
    private static ShowCmtContract.View mView;
    private List<String> mTids;
    private int mPosLabelAll = 1, mPosLabelHot = 0;
    private Map<String, CommentItem> mCommentMap;
    private static final int COMMENT_SIMPLE = 0, COMMENT_COMPLEX = 1, LABEL_ALL = 2, LABEL_HOT = 3;

    @Inject
    public CommentsRvAdapter(FragmentManager fm) {
        mView = (ShowCmtContract.View) fm.findFragmentByTag(CommentsActivity.SHOW_CMT);
        mTids = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mPosLabelHot) {//label for hot comment
            return LABEL_HOT;
        } else if (position < mPosLabelAll) {// content for hot comment
            return getCommentViewType(mTids.get(position));
        } else if (position == mPosLabelAll) {//label for all comment
            return LABEL_ALL;
        } else {//content for all comment
            return getCommentViewType(mTids.get(position));
        }
    }

    private int getCommentViewType(String sid) {
        return TextUtils.equals("0", mCommentMap.get(sid).getPid()) ? COMMENT_SIMPLE : COMMENT_COMPLEX;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case LABEL_HOT:
            case LABEL_ALL:
                view = inflater.inflate(R.layout.tv_label_primary, parent, false);
                viewHolder = new LabelViewHolder(view);
                break;
            case COMMENT_SIMPLE:
                view = inflater.inflate(R.layout.item_comment_simple, parent, false);
                viewHolder = new SimpleViewHolder(view);
                break;
            case COMMENT_COMPLEX:
                view = inflater.inflate(R.layout.item_comment_complex, parent, false);
                viewHolder = new ComplexViewHolder(view);
                break;
            default:
                Log.d(TAG, "onCreateViewHolder:  viewHolder == null");
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CommentItem item = mCommentMap.get(mTids.get(position));
        switch (holder.getItemViewType()) {
            case LABEL_HOT:
                ((LabelViewHolder) holder).tvLabel.setText(mPosLabelAll == 1
                        ? R.string.label_popular_comments_no : R.string.label_popular_comments);
                break;
            case LABEL_ALL:
                ((LabelViewHolder) holder).tvLabel.setText(mTids.size() == 2
                        ? R.string.label_all_comments_no : R.string.label_all_comments);
                break;
            case COMMENT_SIMPLE:
                SimpleViewHolder svh = (SimpleViewHolder) holder;
                svh.origin.setComment(item, svh.itemView.getContext());
                break;
            case COMMENT_COMPLEX:
                ComplexViewHolder cvh = (ComplexViewHolder) holder;
                cvh.origin.setComment(item, cvh.itemView.getContext());
                cvh.reference.setComment(mCommentMap.get(item.getPid()), cvh.itemView.getContext());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mTids.size();
    }

    @Override
    public void clear() {
        mTids.clear();
        notifyDataSetChanged();
    }

    @Override
    public void add(Comments item) {
        mPosLabelHot = 0;
        mTids.add(mPosLabelHot + "");
        mTids.addAll(item.getHotIds());
        mPosLabelAll = 1 + item.getHotIds().size();
        mTids.add(mPosLabelAll + "");
        mTids.addAll(item.getAllIds());
        mCommentMap = item.getCommentMap();
        notifyDataSetChanged();
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
                        mView.toJudgeComment(Action.REPORT.toString(), item.getTid());
                        return true;
                }
                return false;
            }
        });
        popup.show();
    }

    private static class SimpleViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        ImageView ivHead;
        CommentViewGroup origin = new CommentViewGroup();
        CommentViewGroup reference = new CommentViewGroup();

        public SimpleViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ivHead = (ImageView) itemView.findViewById(R.id.iv_comment_photo);
            initView(itemView);
        }

        void initView(View parent) {
            origin.findViews(parent, true);
        }

    }

    private static class ComplexViewHolder extends SimpleViewHolder {

        public ComplexViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        void initView(View parent) {
            origin.findViews(parent, false);
            reference.findViews(parent.findViewById(R.id.inc_comment_ref), true);
        }
    }

    private static class LabelViewHolder extends RecyclerView.ViewHolder {
        TextView tvLabel;

        public LabelViewHolder(View itemView) {
            super(itemView);
            tvLabel = (TextView) itemView.findViewById(R.id.tv_label_primary);
        }
    }

    private static class CommentViewGroup {
        private TextView tvUser;
        private TextView tvDate;
        private TextView tvContent;
        private TextView tvSupport;
        private TextView tvAgainst;
        private TextView tvPopup;

        void findViews(View view, boolean isTogether) {
            //if simple viewHolder or reference viewGroup in complex viewHolder
            //passed view should be direct parent view
            if (isTogether) {
                tvUser = (TextView) view.findViewById(R.id.tv_comment_user);
                tvDate = (TextView) view.findViewById(R.id.tv_comment_date);
                tvContent = (TextView) view.findViewById(R.id.tv_comment_content);
                tvSupport = (TextView) view.findViewById(R.id.tv_comment_support);
                tvAgainst = (TextView) view.findViewById(R.id.tv_comment_against);
                tvPopup = (TextView) view.findViewById(R.id.tv_comment_popup);
            } else {// origin viewGroup in complex viewHolder
                View header = view.findViewById(R.id.inc_comment_header);
                tvUser = (TextView) header.findViewById(R.id.tv_comment_user);
                tvDate = (TextView) header.findViewById(R.id.tv_comment_date);
                View detail = view.findViewById(R.id.inc_comment_detail);
                tvContent = (TextView) detail.findViewById(R.id.tv_comment_content);
                tvSupport = (TextView) detail.findViewById(R.id.tv_comment_support);
                tvAgainst = (TextView) detail.findViewById(R.id.tv_comment_against);
                tvPopup = (TextView) detail.findViewById(R.id.tv_comment_popup);
            }
        }

        void setComment(final CommentItem item, Context context) {
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
                    mView.toJudgeComment(Action.SUPPORT.toString(), item.getTid());
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
                    mView.toJudgeComment(Action.AGAINST.toString(), item.getTid());
                }
            });
        }
    }

}
