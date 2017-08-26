package com.example.digifestmethod;

import com.example.digifestmethod.models.Feedback;
import com.example.digifestmethod.models.Query;
import com.example.digifestmethod.models.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by piyush0 on 18/08/17.
 */

public interface Api {
    @POST("create")
    Call<Query> createQuery(@Body Query query);

    @GET("feed")
    Call<ArrayList<Query>> listQueries();

    @POST("signup")
    Call<User> signup(@Body User user);

    @POST("login")
    Call<User> login(@Body User user);

    @POST("user")
    Call<User> user(@Body User user);

    @POST("resolved")
    Call<Feedback> feedback(@Body Feedback feedback);
}
