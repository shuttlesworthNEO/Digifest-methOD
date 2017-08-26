package com.example.digifestmethod.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.digifestmethod.Api;
import com.example.digifestmethod.R;
import com.example.digifestmethod.models.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ImageView btnMic;
    private Button btnLogin;
    private Button btnSignup;

    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        listeners();
    }

    private void findViews() {
        this.btnMic = (ImageView) findViewById(R.id.btnMic);
        this.btnLogin = (Button) findViewById(R.id.btnLogin);
        this.btnSignup = (Button) findViewById(R.id.btnSignup);
    }

    private void listeners() {
        this.btnMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        this.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, UserformActivity.class);
                startActivity(i);
            }
        });

        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(i);
            }
        });
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech prompt");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), "Not supported",
                    Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String urlToPost = getString(R.string.url);
                    Retrofit retrofit = new Retrofit
                            .Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl(urlToPost)
                            .build();
                    Date date = new Date();
                    Query query = new Query(date.toString(), result.get(0));
                    Api api = retrofit.create(Api.class);
                    api.createQuery(query).enqueue(new Callback<Query>() {
                        @Override
                        public void onResponse(Call<Query> call, Response<Query> response) {
                            if (response.body() != null) {
                                Toast.makeText(MainActivity.this, "Query created", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Query> call, Throwable t) {

                        }
                    });
                }

                break;
            }

        }
    }

}
