package com.example.basicviews.api;

import com.example.basicviews.models.Category;
import com.example.basicviews.models.LoginRequest;
import com.example.basicviews.models.LoginResponse;
import com.example.basicviews.models.Product;
import com.example.basicviews.models.ProductResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @GET("products/categories")
    Call<List<Category>> getCategories();



    @GET("products/category/{category}")
    Call<ProductResponse> getProductsByCategory(@Path("category") String categoryName);

}
