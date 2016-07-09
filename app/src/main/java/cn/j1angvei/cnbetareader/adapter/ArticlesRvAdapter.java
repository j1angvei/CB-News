package cn.j1angvei.cnbetareader.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.bean.Article;
import cn.j1angvei.cnbetareader.util.DateUtil;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Wayne on 2016/7/4.
 */
public class ArticlesRvAdapter extends RecyclerView.Adapter<ArticlesRvAdapter.ViewHolder> implements SwipeAdapter<Article> {

    private List<Article> mArticles;
    private Activity mActivity;

    public ArticlesRvAdapter(Activity activity) {
        mActivity = activity;
        mArticles = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View articleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ViewHolder(articleView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        final Article article = mArticles.get(position);
        holder.tvTitle.setText(Html.fromHtml(article.getTitle()).toString());
        holder.tvSummary.setText(Html.fromHtml(article.getSummary()).toString());
        holder.tvViewer.setText(article.getCounterNum());
        holder.tvComments.setText(article.getCommentNum());
        Date date = article.getTime();
        holder.tvTime.setText(DateUtil.toShortDatePlusTime(date, context));
        Glide.with(context).load(article.getThumb())
                .bitmapTransform(new CropCircleTransformation(context))
                .into(holder.ivThumb);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(holder.itemView, "hei hei" + article.getId(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    @Override
    public void clear() {
        int size = mArticles.size();
        mArticles.clear();
        notifyItemRangeRemoved(0, size);
    }

    @Override
    public void add(Article item) {
        mArticles.add(item);
        notifyItemInserted(mArticles.size() - 1);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_article_title)
        TextView tvTitle;
        @BindView(R.id.tv_article_summary)
        TextView tvSummary;
        @BindView(R.id.iv_article_thumb)
        ImageView ivThumb;
        @BindView(R.id.tv_article_count)
        TextView tvViewer;
        @BindView(R.id.tv_article_comment)
        TextView tvComments;
        @BindView(R.id.tv_article_time)
        TextView tvTime;

        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
