package cn.j1angvei.cnbetareader.fragment;

import android.support.v4.app.FragmentManager;
import android.view.View;

import cn.j1angvei.cnbetareader.adapter.ExploreRvAdapter;
import cn.j1angvei.cnbetareader.bean.Topic;
import cn.j1angvei.cnbetareader.di.component.ActivityComponent;
import cn.j1angvei.cnbetareader.fragment.dialog.SeekPageDialog;

/**
 * Created by Wayne on 2016/7/13.
 */
public class TopicFragment extends NewsFragment<Topic, ExploreRvAdapter.ViewHolder> {

    public static TopicFragment newInstance(String type) {
        TopicFragment fragment = new TopicFragment();
        fragment.setBundle(type);
        return fragment;
    }

    @Override
    public void setLayoutManager() {
        mRecyclerView.setLayoutManager(mGridLayoutManager);
    }

    @Override
    protected void inject(ActivityComponent component) {
//        component.exploreComponent(new ExploreModule()).inject(this);
    }
//
//    @Override
//    protected void retrieveTopic() {
//        mPresenter.retrieveTopics(mPage++);
//    }

    @Override
    public void onClick(View view) {
        FragmentManager fm = getFragmentManager();
        SeekPageDialog dialog = SeekPageDialog.newInstance(1);
        dialog.setTargetFragment(TopicFragment.this, 300);
        dialog.show(fm, "explore");
    }
}
