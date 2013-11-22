package com.twitter.android.yamba;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.*;

public class TweetActivity extends ActionBarActivity
        implements View.OnClickListener {

    private static final String TAG = "TweetActivity";

    EditText mEditTweet;
    YambaClient mYambaClient;
    Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (BuildConfig.DEBUG) Log.v(TAG, "onCreate() invoked");

        setContentView(R.layout.activity_tweet);
        mEditTweet = (EditText) findViewById(R.id.edit_tweet);
        Button buttonTweet = (Button) findViewById(R.id.button_tweet);
        buttonTweet.setOnClickListener(this);

        mToast = Toast.makeText(this, null, Toast.LENGTH_LONG);

        mYambaClient = new YambaClient("student", "password");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id) {
            case R.id.button_tweet:
                // Tweet button clicked
                if (BuildConfig.DEBUG) Log.v(TAG, "Tweet button clicked");
                String msg = mEditTweet.getText().toString();
                mEditTweet.setText("");
                if (BuildConfig.DEBUG) Log.v(TAG, "User entered: " + msg);

                if (!TextUtils.isEmpty(msg)) {
                    new PostToTwitter().execute(msg);
                }
                break;
            default:
                // We should never get here!
                Log.w(TAG, "Unknown button clicked");
        }
    }

   class PostToTwitter extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            int ret = R.string.post_tweet_fail;
            if (params.length > 0) {
                try {
                    mYambaClient.postStatus(params[0]);
                    ret = R.string.post_tweet_success;
                } catch (YambaClientException e) {
                    Log.w(TAG, "Failed to post message", e);
                }
            }
            return ret;
        }

        @Override
        protected void onPostExecute(Integer result) {
            mToast.setText(result);
            mToast.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tweet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
