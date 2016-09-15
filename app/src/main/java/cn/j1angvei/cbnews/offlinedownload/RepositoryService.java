package cn.j1angvei.cbnews.offlinedownload;

import javax.inject.Inject;

import cn.j1angvei.cbnews.base.BaseService;
import cn.j1angvei.cbnews.base.Repository;
import cn.j1angvei.cbnews.bean.Article;
import cn.j1angvei.cbnews.bean.Comments;
import cn.j1angvei.cbnews.bean.Content;
import cn.j1angvei.cbnews.bean.Headline;
import cn.j1angvei.cbnews.bean.Review;
import cn.j1angvei.cbnews.di.component.DaggerServiceComponent;
import cn.j1angvei.cbnews.di.module.ServiceModule;
import cn.j1angvei.cbnews.di.qualifier.QArticle;
import cn.j1angvei.cbnews.di.qualifier.QCmt;
import cn.j1angvei.cbnews.di.qualifier.QContent;
import cn.j1angvei.cbnews.di.qualifier.QHeadline;
import cn.j1angvei.cbnews.di.qualifier.QReview;

/**
 * Created by Wayne on 2016/9/15.
 */

public abstract class RepositoryService extends BaseService {
    private static final String TAG = "RepositoryService";
    @Inject
    @QArticle
    Repository<Article> mArticleRepository;
    @Inject
    @QHeadline
    Repository<Headline> mHeadlineRepository;
    @Inject
    @QReview
    Repository<Review> mReviewRepository;
    @Inject
    @QContent
    Repository<Content> mContentRepository;
    @Inject
    @QCmt
    Repository<Comments> mCmtRepository;

    public RepositoryService() {
        super(TAG);
    }

    @Override
    protected void doInjection() {
        mServiceComponent = DaggerServiceComponent.builder()
                .applicationComponent(getApplicationComponent())
                .serviceModule(new ServiceModule(this))
                .build();
        mServiceComponent.inject(this);
    }

}
