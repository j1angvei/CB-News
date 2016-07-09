package cn.j1angvei.cnbetareader.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.bean.Review;

/**
 * Created by Wayne on 2016/7/5.
 */
public class ReviewRvAdapter extends SwipeAdapter<Review, ReviewRvAdapter.ViewHolder> {
    private List<Review> mReviews;
    private Activity mActivity;

    public ReviewRvAdapter(Activity activity) {
        mActivity = activity;
        mReviews = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        final Review review = mReviews.get(position);
        holder.tvComment.setText(review.getComment());
        String phLocation = context.getResources().getString(R.string.ph_review_location);
        holder.tvLocation.setText(String.format(phLocation, review.getLocation()));
        holder.tvTitle.setText(review.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(holder.itemView, "ha ha" + review.getArticleId(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    @Override
    public void clear() {
        int size = mReviews.size();
        mReviews.clear();
        notifyItemRangeRemoved(0, size);
    }

    @Override
    public void add(Review item) {
        mReviews.add(item);
        notifyItemInserted(mReviews.size() - 1);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_review_comment)
        TextView tvComment;
        @BindView(R.id.tv_review_location)
        TextView tvLocation;
        @BindView(R.id.tv_review_title)
        TextView tvTitle;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
