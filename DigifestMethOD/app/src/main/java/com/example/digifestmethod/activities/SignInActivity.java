package com.example.digifestmethod.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.digifestmethod.Api;
import com.example.digifestmethod.R;
import com.example.digifestmethod.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignInActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);


        sharedPreferences = getSharedPreferences(getString(R.string.sp_user), MODE_PRIVATE);
        findViews();
        listeners();
    }

    private void findViews() {
        this.etUsername = (EditText) findViewById(R.id.etUsernameSignIn);
        this.etPassword = (EditText) findViewById(R.id.etPasswordSingIn);
        this.btnLogin = (Button) findViewById(R.id.btnLoginSignIn);
    }

    private void listeners() {
        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlToPost = getString(R.string.url);
                Retrofit retrofit = new Retrofit
                        .Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(urlToPost)
                        .build();
                User user = new User(etUsername.getText().toString(), etPassword.getText().toString());

                Api api = retrofit.create(Api.class);
                api.login(user).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.body() != null) {

                            if (response.body().getUsername().equals("null")) {
                                Toast.makeText(SignInActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                            } else {
                                sharedPreferences.edit().putString("username", response.body().getUsername()).apply();
                                Intent i = new Intent(SignInActivity.this, FeedActivity.class);
                                startActivity(i);
                            }
                        } else {
                            Toast.makeText(SignInActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });


            }
        });
    }
}
