package cn.j1angvei.cnbetareader.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by Wayne on 2016/8/31.
 */

public class OfflineDownloadService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public OfflineDownloadService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
