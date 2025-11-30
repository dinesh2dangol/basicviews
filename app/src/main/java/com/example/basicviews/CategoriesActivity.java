package com.example.basicviews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.util.Log;
import android.widget.Toast;

import com.example.basicviews.api.ApiService;
import com.example.basicviews.api.RetrofitClient;
import com.example.basicviews.models.Category;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        listView = findViewById(R.id.listViewCategories);

        loadCategories();
    }

    private void loadCategories() {
        ApiService apiService = RetrofitClient.getApi();

        apiService.getCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    List<Category> categories = response.body();

                    if (categories == null) {
                        Toast.makeText(CategoriesActivity.this, "No data", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    List<String> names = new ArrayList<>();
//                    names.add("Apple");
//                    names.add("Banana");
                    for (Category c : categories) {
                        names.add(c.name);
                    }
                    Log.e("sss", "Category names: " + names);


                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            CategoriesActivity.this,
                            android.R.layout.simple_list_item_1,
                            names
                    );

                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener((parent, view, position, id) -> {
                        String selectedCategory = names.get(position);
                        Toast.makeText(CategoriesActivity.this, selectedCategory, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(CategoriesActivity.this, ProductsActivity.class);
                        intent.putExtra("categoryName", selectedCategory);
                        startActivity(intent);
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e("API", "Error: " + t.getMessage());
            }
        });
    }
}
