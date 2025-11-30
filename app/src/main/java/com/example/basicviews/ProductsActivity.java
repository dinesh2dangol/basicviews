package com.example.basicviews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.basicviews.api.ApiService;
import com.example.basicviews.api.RetrofitClient;
import com.example.basicviews.models.Product;
import com.example.basicviews.models.ProductResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends AppCompatActivity {

    ListView listViewProducts;
    String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        listViewProducts = findViewById(R.id.listViewProducts);

        categoryName = getIntent().getStringExtra("categoryName");

        if (categoryName == null) {
            Toast.makeText(this, "Category not received!", Toast.LENGTH_SHORT).show();
            return;
        }

        loadProducts(categoryName.toLowerCase());
    }

    private void loadProducts(String category) {
        ApiService api = RetrofitClient.getApi();

        api.getProductsByCategory(category).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {

                if (!response.isSuccessful() || response.body() == null) {
                    Log.e("API", "Error: " + response.code());
                    Toast.makeText(ProductsActivity.this, "API Error!", Toast.LENGTH_SHORT).show();
                    return;
                }

                ProductResponse data = response.body();

                if (data.products == null) {
                    Log.e("API", "Products is null");
                    return;
                }

                List<Product> list = data.products;

                // Log product titles
                for (Product p : list) {
                    Log.e("API", "Product: " + p.title);
                }

                // Convert product titles to String list
                List<String> names = new ArrayList<>();
                for (Product p : list) {
                    names.add(p.title);
                }

                // Display in ListView
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        ProductsActivity.this,
                        android.R.layout.simple_list_item_1,
                        names
                );

                listViewProducts.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.e("API", "Failed: " + t.getMessage());
                Toast.makeText(ProductsActivity.this, "Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
