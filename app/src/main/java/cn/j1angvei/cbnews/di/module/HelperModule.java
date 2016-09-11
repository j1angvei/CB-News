package cn.j1angvei.cbnews.di.module;

import cn.j1angvei.cbnews.bean.Article;
import cn.j1angvei.cbnews.bean.Bookmark;
import cn.j1angvei.cbnews.bean.Comments;
import cn.j1angvei.cbnews.bean.Content;
import cn.j1angvei.cbnews.bean.Headline;
import cn.j1angvei.cbnews.bean.Review;
import cn.j1angvei.cbnews.bean.Topic;
import cn.j1angvei.cbnews.data.local.helper.ArticleDbHelper;
import cn.j1angvei.cbnews.data.local.helper.BookmarkDbHelper;
import cn.j1angvei.cbnews.data.local.helper.CommentDbHelper;
import cn.j1angvei.cbnews.data.local.helper.ContentDbHelper;
import cn.j1angvei.cbnews.data.local.helper.DbHelper;
import cn.j1angvei.cbnews.data.local.helper.HeadlineDbHelper;
import cn.j1angvei.cbnews.data.local.helper.ReviewDbHelper;
import cn.j1angvei.cbnews.data.local.helper.TopicDbHelper;
import cn.j1angvei.cbnews.di.qualifier.QArticle;
import cn.j1angvei.cbnews.di.qualifier.QBookmark;
import cn.j1angvei.cbnews.di.qualifier.QCmt;
import cn.j1angvei.cbnews.di.qualifier.QContent;
import cn.j1angvei.cbnews.di.qualifier.QHeadline;
import cn.j1angvei.cbnews.di.qualifier.QReview;
import cn.j1angvei.cbnews.di.qualifier.QTopic;
import dagger.Binds;
import dagger.Module;

/**
 * Created by Wayne on 2016/9/10.
 */
@Module
public abstract class HelperModule {

    @Binds
    @QArticle
    abstract DbHelper<Article> bindsADbHelper(ArticleDbHelper helper);

    @Binds
    @QReview
    abstract DbHelper<Review> bindsRDbHelper(ReviewDbHelper helper);

    @Binds
    @QHeadline
    abstract DbHelper<Headline> bindsHDbHelper(HeadlineDbHelper helper);

    @Binds
    @QCmt
    abstract DbHelper<Comments> bindsMDbHelper(CommentDbHelper helper);

    @Binds
    @QContent
    abstract DbHelper<Content> bindsCDbHelper(ContentDbHelper helper);

    @Binds
    @QTopic
    abstract DbHelper<Topic> bindsTDbHelper(TopicDbHelper helper);

    @Binds
    @QBookmark
    abstract DbHelper<Bookmark> bindsBDdHelper(BookmarkDbHelper helper);
}
