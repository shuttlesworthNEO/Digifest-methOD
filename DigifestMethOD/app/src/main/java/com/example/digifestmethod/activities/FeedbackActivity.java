package com.example.digifestmethod.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.digifestmethod.Api;
import com.example.digifestmethod.R;
import com.example.digifestmethod.models.Feedback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public class FeedbackActivity extends AppCompatActivity {

    private Button btnYes;
    private Button btnNo;
    private String queryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        queryId = getIntent().getStringExtra("queryId");
        findViews();
        listeners();
    }

    private void listeners() {


        this.btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFeedback(1);
            }
        });


        this.btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFeedback(0);
            }
        });
    }

    private void sendFeedback(Integer resolved) {
        String urlToPost = getString(R.string.url);
        Retrofit retrofit = new Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(urlToPost)
                .build();

        Api api = retrofit.create(Api.class);

        Feedback feedback = new Feedback(resolved, queryId, getSharedPreferences(getString(R.string.sp_user), MODE_PRIVATE).getString("username", "null"));
        api.feedback(feedback).enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                Intent i = new Intent(FeedbackActivity.this, MainActivity.class);
                startActivity(i);
            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {

            }
        });
    }

    private void findViews() {
        this.btnYes = (Button) findViewById(R.id.btnYes);
        this.btnNo = (Button) findViewById(R.id.btnNo);
    }
}
