package com.example.basicviews;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.example.basicviews.databinding.ActivityContactBinding;
import com.example.basicviews.databinding.ActivityMainBinding;

import org.w3c.dom.Text;

public class ContactActivity extends AppCompatActivity {
    private ActivityContactBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact);

        TextView t1=(TextView) findViewById(R.id.textView1);
        t1.setText("Text changed");

        Button b1 = (Button) findViewById(R.id.button1);
        b1.setText("SunFun");
    }
}