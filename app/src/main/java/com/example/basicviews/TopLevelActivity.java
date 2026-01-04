package com.example.basicviews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TopLevelActivity extends AppCompatActivity {
    private DBHelper mydb ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);
        mydb = new DBHelper(this);
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                    Toast.makeText(TopLevelActivity.this,"Drink", Toast.LENGTH_SHORT).show();
                else if(position==1){
                    Intent intent = new Intent(TopLevelActivity.this, FoodActivity.class);
                    intent.putExtra("name","Ram");
                    startActivity(intent);
                }else if(position==2){
                    Intent intent = new Intent(TopLevelActivity.this, MemberActivity.class);
                    intent.putExtra("name","Ram");
                    startActivity(intent);
                }
                else if(position==3){
                    Bundle dataBundle = new Bundle();
                    dataBundle.putInt("id", 0);

                    Intent intent = new Intent(getApplicationContext(),DisplayActivity.class);
                    intent.putExtras(dataBundle);

                    startActivity(intent);
                }
                else {
                    int n = mydb.numberOfRows();
                    Toast.makeText(getApplicationContext(),"n:"+n, Toast.LENGTH_SHORT).show();
//                    mydb.insertContact("Ram","9841","email","Kathmandu","Bhotebahal");
                }
            }
        };
        ListView listView = (ListView) findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);

    }
}