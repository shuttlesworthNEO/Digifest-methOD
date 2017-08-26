package com.example.digifestmethod.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
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

public class UserformActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private EditText etPasswordConfirm;
    private Button btnSubmit;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userform);
        sharedPreferences = getSharedPreferences(getString(R.string.sp_user), MODE_PRIVATE);
        findViews();
        listeners();
    }

    private void listeners() {

        this.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String urlToPost = getString(R.string.url);
                Retrofit retrofit = new Retrofit
                        .Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(urlToPost)
                        .build();

                final User user = new User(etUsername.getText().toString(), etPassword.getText().toString());

                Api api = retrofit.create(Api.class);
                api.signup(user).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.body() != null) {

                            Toast.makeText(UserformActivity.this, "User created", Toast.LENGTH_SHORT).show();
                            sharedPreferences.edit().putString("username", user.getUsername()).apply();
                            Intent i = new Intent(UserformActivity.this, FeedActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(UserformActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });


            }
        });
    }

    private void findViews() {
        this.etUsername = (EditText) findViewById(R.id.etUsernameSignUp);
        this.etPassword = (EditText) findViewById(R.id.etPasswordSignUp);
        this.etPasswordConfirm = (EditText) findViewById(R.id.etPasswordConfirmSignUp);
        this.btnSubmit = (Button) findViewById(R.id.btnSubmitSignup);
    }


}
