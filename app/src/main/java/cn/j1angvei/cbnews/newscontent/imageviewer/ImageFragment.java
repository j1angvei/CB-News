package cn.j1angvei.cbnews.newscontent.imageviewer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.j1angvei.cbnews.R;
import cn.j1angvei.cbnews.di.component.ActivityComponent;
import cn.j1angvei.cbnews.base.BaseFragment;

/**
 * Created by Wayne on 2016/9/4.
 * show content image
 */

public class ImageFragment extends BaseFragment {
    private static final String URL = "ImageFragment";
    @BindView(R.id.iv_content_images)
    ImageView ivPhoto;
    private String mUrl;

    public static ImageFragment newInstance(String url) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString(URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void inject(ActivityComponent component) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUrl = getArguments().getString(URL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Glide.with(this).load(mUrl).into(ivPhoto);
    }
}
