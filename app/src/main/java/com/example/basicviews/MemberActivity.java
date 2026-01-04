package com.example.basicviews;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MemberActivity extends AppCompatActivity {
    private ListView listView;
    private DBHelper dbHelper;

    private ArrayList<Contact> contacts;
    ArrayAdapter<Contact> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_member);
        listView = findViewById(R.id.list_contacts);
        dbHelper = new DBHelper(this);
        loadContacts();
        // Long press to delete
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                final Contact contact = contacts.get(position);

                new AlertDialog.Builder(MemberActivity.this)
                        .setTitle("Delete Contact")
                        .setMessage("Are you sure you want to delete " + contact.name + "?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                boolean deleted = dbHelper.deleteContactById(contact.id);
                                if (deleted) {
                                    Toast.makeText(MemberActivity.this, "Contact deleted", Toast.LENGTH_SHORT).show();
                                    loadContacts(); // refresh list
                                } else {
                                    Toast.makeText(MemberActivity.this, "Failed to delete contact", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });
    }
    private void loadContacts() {
        contacts = dbHelper.getAllContacts();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts);
        listView.setAdapter(adapter);
    }
}