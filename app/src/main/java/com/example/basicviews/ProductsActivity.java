package com.example.basicviews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.basicviews.adapters.ProductAdapter;
import com.example.basicviews.api.ApiService;
import com.example.basicviews.api.RetrofitClient;
import com.example.basicviews.models.Product;
import com.example.basicviews.models.ProductResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends AppCompatActivity {

    RecyclerView recyclerProducts;
    String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        recyclerProducts = findViewById(R.id.recyclerProducts);
        recyclerProducts.setLayoutManager(new LinearLayoutManager(this));

        categoryName = getIntent().getStringExtra("categoryName");

        loadProducts(categoryName);
    }

    private void loadProducts(String category) {
        ApiService api = RetrofitClient.getApi();

        api.getProductsByCategory(category).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Log.e("API", "Failed: empty response");
                    return;
                }

                List<Product> products = response.body().products;

                ProductAdapter adapter = new ProductAdapter(ProductsActivity.this, products);
                recyclerProducts.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.e("API", "Failed: " + t.getMessage());
            }
        });
    }
}
