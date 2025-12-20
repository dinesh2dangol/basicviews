package com.example.basicviews;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.example.basicviews.databinding.ActivityContactBinding;
import com.google.android.material.snackbar.Snackbar;

public class ContactActivity extends AppCompatActivity {
    private ActivityContactBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_contact);
        binding = ActivityContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        binding.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.textView1.setText("7:00-10:00: MAD");
            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.textView1.setText("7:00-10:00: Economics\n10:00-11:00: Break\n12:00-13:00: PP");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contact, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            final CharSequence[] items = {"Red","Blue"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("test");
            builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), items[which], Toast.LENGTH_SHORT).show();

                }
            });
            builder.show();

        }
        if (id == R.id.action_about) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("today","Sun");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}