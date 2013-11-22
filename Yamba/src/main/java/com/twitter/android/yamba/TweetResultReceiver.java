package com.twitter.android.yamba;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class TweetResultReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        boolean result = intent.getBooleanExtra(TwitterService.EXTRA_POST_TWEET_RESULT, false);
        Toast.makeText(
                context,
                result ? R.string.post_tweet_success : R.string.post_tweet_fail,
                Toast.LENGTH_LONG
        ).show();
    }
}
