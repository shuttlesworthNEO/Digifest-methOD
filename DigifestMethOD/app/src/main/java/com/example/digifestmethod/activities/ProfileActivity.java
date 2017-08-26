package com.example.digifestmethod.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digifestmethod.Api;
import com.example.digifestmethod.R;
import com.example.digifestmethod.models.User;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvUsername;
    private TextView tvScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        findViews();
        getUser();
    }

    private void getUser() {
        String urlToPost = getString(R.string.url);
        Retrofit retrofit = new Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(urlToPost)
                .build();
        User user = new User(getSharedPreferences(getString(R.string.sp_user), MODE_PRIVATE).getString("username", "null"), "null");
        Api api = retrofit.create(Api.class);
        api.user(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() != null) {

                    tvUsername.setText(response.body().getUsername());
                    tvScore.setText(response.body().getScore());
                } else {
                    Toast.makeText(ProfileActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void findViews() {
        this.tvUsername = (TextView) findViewById(R.id.tv_profileUsername);
        this.tvScore = (TextView) findViewById(R.id.tv_profileScore);
    }
}
