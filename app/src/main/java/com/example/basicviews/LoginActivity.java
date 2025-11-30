package com.example.basicviews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.basicviews.api.ApiService;
import com.example.basicviews.api.RetrofitClient;
import com.example.basicviews.databinding.ActivityLoginBinding;
import com.example.basicviews.models.LoginRequest;
import com.example.basicviews.models.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(v -> {
            String username = binding.inputUsername.getText().toString();
            String password = binding.inputPassword.getText().toString();

            doLogin(username, password);
        });
    }

    private void doLogin(String username, String password) {
        Toast.makeText(LoginActivity.this,
                "Logged in ...",
                Toast.LENGTH_SHORT).show();

        ApiService api = RetrofitClient.getApi();

        LoginRequest req = new LoginRequest(username, password);

        api.login(req).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    LoginResponse login = response.body();

                    Toast.makeText(LoginActivity.this,
                            "Logged in as: " + login.username,
                            Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(LoginActivity.this,
                            "Invalid username or password",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this,
                        "Failed: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
