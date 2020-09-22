package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {
    EditText etTweetInput;
    Button btnSend;
    TwitterClient client;
    TextView tvCharCount;
    public static final int RESULT_TWEET_KEY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        etTweetInput = findViewById(R.id.etTweetInput);
        btnSend = findViewById(R.id.btnSend);
        client = TwitterApp.getRestClient(this);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTweet();
            }
        });
        tvCharCount = (TextView) findViewById(R.id.tvCharCount);

        etTweetInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int charLeft = 280 - etTweetInput.getText().length();
                tvCharCount.setText(Integer.toString(charLeft));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


        private void sendTweet() {
            client.sendTweet(etTweetInput.getText().toString(), new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        Tweet tweet = Tweet.fromJSON(response);
                        Intent intent = new Intent();
                        intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                        setResult(RESULT_OK, intent);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    super.onSuccess(statusCode, headers, response);
                }
            });

        }
}
