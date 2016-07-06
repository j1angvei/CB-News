package cn.j1angvei.cnbetareader.newslist.pastheadlines;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import cn.j1angvei.cnbetareader.R;
import cn.j1angvei.cnbetareader.base.BaseAdapter;
import cn.j1angvei.cnbetareader.bean.Headline;
import cn.j1angvei.cnbetareader.bean.RelatedItem;

/**
 * Created by Wayne on 2016/7/5.
 */
public class HeadlineRvAdapter extends RecyclerView.Adapter<HeadlineRvAdapter.ViewHolder> implements BaseAdapter<Headline> {
    private static final String TAG = "HeadlineRvAdapter";
    private List<Headline> mHeadlines;
    private Activity mActivity;

    public HeadlineRvAdapter(Activity activity) {
        mActivity = activity;
        mHeadlines = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_headline, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        final Headline headline = mHeadlines.get(position);
        holder.tvTitle.setText(headline.getTitle());
        holder.tvDescription.setText(headline.getContent());
        //relate items
        List<RelatedItem> relatedItems = headline.getRelatedArticles();
        if (relatedItems.size() <= 0) {
            holder.llRelateContainer.setVisibility(View.GONE);
        } else {
            holder.llRelateContainer.setVisibility(View.VISIBLE);
            Glide.with(context).load(headline.getThumb()).into(holder.ivThumb);
            for (int i = 0; i < relatedItems.size(); i++) {
                TextView tv = holder.tvRelate.get(i);
                final RelatedItem item = headline.getRelatedArticles().get(i);
                tv.setVisibility(View.VISIBLE);
                tv.setText(item.getTitle());
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(holder.itemView, "ha ha" + item.getSid(), Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return mHeadlines.size();
    }

    @Override
    public void clear() {
        int size = mHeadlines.size();
        mHeadlines.clear();
        notifyItemRangeRemoved(0, size);

    }

    @Override
    public void add(Headline item) {
        mHeadlines.add(item);
        notifyItemInserted(mHeadlines.size() - 1);
    }

    @Override
    public ArrayList<String> gatherNewsIds() {
        return null;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_headline_title)
        TextView tvTitle;
        @BindView(R.id.tv_headline_description)
        TextView tvDescription;
        @BindView(R.id.iv_headline_thumb)
        ImageView ivThumb;
        @BindViews({R.id.tv_headline_relate_1, R.id.tv_headline_relate_2, R.id.tv_headline_relate_3})
        List<TextView> tvRelate;

        LinearLayout llRelateContainer;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            llRelateContainer = (LinearLayout) itemView.findViewById(R.id.ll_headline_relate_container);
            ButterKnife.bind(this, itemView);
        }
    }
}
