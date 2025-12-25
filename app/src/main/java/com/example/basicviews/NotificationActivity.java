package com.example.basicviews;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NotificationActivity extends AppCompatActivity {
    Button b1;
    private static final String CHANNEL_ID = "default_channel";

    private void addNotification() {

        // 1️⃣ Create Notification Channel (Android 8+)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Default Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Test notification channel");

            NotificationManager manager =
                    getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        // 2️⃣ Intent when notification is clicked
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // 3️⃣ Build notification
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_background) // use proper icon
                        .setContentTitle("Notifications Example")
                        .setContentText("This is a test notification2")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true)
                        .setContentIntent(contentIntent);

        // 4️⃣ Show notification
        NotificationManager manager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        b1 = (Button)findViewById(R.id.button_notify);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= 33) {
                    if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(
                                new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                                100
                        );
                        return; // wait for user response
                    }
                }

                addNotification();
            }
        });
    }

}
