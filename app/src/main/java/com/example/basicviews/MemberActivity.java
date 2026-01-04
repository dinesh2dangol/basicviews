package com.example.basicviews;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
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

                // Show options dialog: Delete or Update
                CharSequence[] options = {"Edit", "Delete"};
                new AlertDialog.Builder(MemberActivity.this)
                        .setTitle(contact.name)
                        .setItems(options, (dialog, which) -> {
                            if (which == 0) {
                                showUpdateDialog(contact); // Update
                            } else {
                                deleteContact(contact); // Delete
                            }
                        })
                        .show();

                return true;
            }
        });
    }

    private void deleteContact(final Contact contact) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Contact")
                .setMessage("Are you sure you want to delete " + contact.name + "?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    boolean deleted = dbHelper.deleteContactById(contact.id);
                    if (deleted) {
                        Toast.makeText(MemberActivity.this, "Contact deleted", Toast.LENGTH_SHORT).show();
                        loadContacts();
                    } else {
                        Toast.makeText(MemberActivity.this, "Failed to delete contact", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void showUpdateDialog(final Contact contact) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Contact");

        // LinearLayout to hold two EditTexts
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10);

        final EditText inputName = new EditText(this);
        inputName.setHint("Name");
        inputName.setText(contact.name);
        inputName.setInputType(InputType.TYPE_CLASS_TEXT);
        layout.addView(inputName);

        final EditText inputPhone = new EditText(this);
        inputPhone.setHint("Phone");
        inputPhone.setText(contact.phone);
        inputPhone.setInputType(InputType.TYPE_CLASS_PHONE);
        layout.addView(inputPhone);

        builder.setView(layout);

        builder.setPositiveButton("Update", (dialog, which) -> {
            String newName = inputName.getText().toString().trim();
            String newPhone = inputPhone.getText().toString().trim();
            if (newName.isEmpty() || newPhone.isEmpty()) {
                Toast.makeText(MemberActivity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            } else {
                boolean updated = dbHelper.updateContact(contact.id, newName, newPhone);
                if (updated) {
                    Toast.makeText(MemberActivity.this, "Contact updated", Toast.LENGTH_SHORT).show();
                    loadContacts();
                } else {
                    Toast.makeText(MemberActivity.this, "Failed to update contact", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
    private void loadContacts() {
        contacts = dbHelper.getAllContacts();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts);
        listView.setAdapter(adapter);
    }
}