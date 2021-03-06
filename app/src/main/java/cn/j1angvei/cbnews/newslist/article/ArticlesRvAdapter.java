package cn.j1angvei.cbnews.newslist.article;

import android.content.Context;
import android.support.v4.app.Fragment;
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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cbnews.R;
import cn.j1angvei.cbnews.bean.Article;
import cn.j1angvei.cbnews.di.scope.PerFragment;
import cn.j1angvei.cbnews.newslist.NewsAdapter;
import cn.j1angvei.cbnews.util.DateUtil;
import cn.j1angvei.cbnews.util.Navigator;

/**
 * Created by Wayne on 2016/7/4.
 * adapter display Article
 */
@PerFragment
public class ArticlesRvAdapter extends NewsAdapter<Article, ArticlesRvAdapter.ViewHolder> {

    private ArrayList<Article> mArticles;
    private Fragment mView;

    @Inject
    public ArticlesRvAdapter(Fragment fragment) {
        mView = fragment;
        mArticles = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Context context = holder.itemView.getContext();
        final Article article = mArticles.get(position);
        holder.tvTitle.setText(Html.fromHtml(article.getTitle()).toString());
        holder.tvSummary.setText(Html.fromHtml(article.getSummary()).toString());
        holder.tvViewer.setText(article.getViewerNum());
        holder.tvComments.setText(article.getCommentNum());
        Date date = article.getTime();
        holder.tvTime.setText(DateUtil.toShortDatePlusTime(date, context));
        Glide.with(context).load(article.getThumb()).into(holder.ivThumb);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigator.toContent(holder.getAdapterPosition(), mArticles, mView.getActivity());
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

    @Override
    public void add(List<Article> items) {
        mArticles.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public ArrayList<String> getSids() {
        ArrayList<String> allSid = new ArrayList<>();
        for (Article article : mArticles) {
            allSid.add(article.getSid());
        }
        return allSid;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
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
