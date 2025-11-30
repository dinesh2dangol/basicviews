package com.example.basicviews.api;

import com.example.basicviews.models.LoginRequest;
import com.example.basicviews.models.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

}
