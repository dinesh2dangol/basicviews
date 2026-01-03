package com.example.basicviews;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("products/1")
    Call<Product> getProduct();
}

