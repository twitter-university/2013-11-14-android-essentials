package com.twitter.android.yamba;

import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class TwitterService extends IntentService {
    private static final String TAG = "TwitterService";

    public static final String ACTION_POST_TWEET_RESULT = "ACTION_POST_TWEET_RESULT";
    public static final String EXTRA_TWEET_MSG = "EXTRA_TWEET_MSG";
    public static final String EXTRA_POST_TWEET_RESULT = "EXTRA_POST_TWEET_RESULT";

    YambaClient mYambaClient;

    public TwitterService() {
        // Base constructor takes name of worker thread for debugging purposes
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mYambaClient = new YambaClient("student", "password");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (BuildConfig.DEBUG) Log.v(TAG, "onHandleIntent() invoked");
        boolean result = false;
        String msg = intent.getStringExtra(EXTRA_TWEET_MSG);
        if (!TextUtils.isEmpty(msg)) {
            try {
                mYambaClient.postStatus(msg);
                Log.d(TAG, "Successfully posted message: " + msg);
                result = true;
            } catch (YambaClientException e) {
                Log.w(TAG, "Failed to post message", e);
            }
        }
        Intent response = new Intent(ACTION_POST_TWEET_RESULT);
        response.putExtra(EXTRA_POST_TWEET_RESULT, result);
        sendBroadcast(response);
    }
}
