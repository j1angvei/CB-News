package cn.j1angvei.cbnews.offlinedownload;

import android.content.Intent;
import android.util.Log;

/**
 * Created by Wayne on 2016/9/15.
 */

public class SaveCacheService extends RepositoryService {
    public static final String TAG = "SaveCacheService";

    @Override
    protected void onHandleIntent(Intent intent) {
        mArticleRepository.updateLocal();
        Log.d(TAG, "onHandleIntent: save article finished");
        mHeadlineRepository.updateLocal();
        Log.d(TAG, "onHandleIntent: save content finished");
        mReviewRepository.updateLocal();
        Log.d(TAG, "onHandleIntent: save review finished");
        mContentRepository.updateLocal();
        Log.d(TAG, "onHandleIntent: save content finished");
        mCmtRepository.updateLocal();
        Log.d(TAG, "onHandleIntent: save cmt finished");
    }
}
