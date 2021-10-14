package com.example.workoutapp;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationO2 extends AppCompatActivity {

    Button notifyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();

        notifyBtn = findViewById(R.id.notify_btn);

        notifyBtn.setOnClickListener(v -> {
//            @Override
//            public void onClick(View v) {
                //notification code goes here
                Toast.makeText(this, "Reminder Set", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(NotificationO2.this, ReminderBroadcast.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(NotificationO2.this, 0, intent, 0);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                long timeAtButtonClick = System.currentTimeMillis();

                long tenSecondsInMillis = 1000 * 10;

                alarmManager.set(AlarmManager.RTC_WAKEUP,
                        timeAtButtonClick + tenSecondsInMillis,
                        pendingIntent);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationO2.this, "My Notification");
                builder.setContentTitle("My Notification");
                builder.setContentText("insert workout notification here");
                builder.setSmallIcon(R.drawable.ic_launcher_background);
                builder.setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(NotificationO2.this);
                managerCompat.notify(1, builder.build());
        });

    }

        private void createNotificationChannel() {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "LemubitReminderChannel";
                String description = "Channel for Lemubit Reminder";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", importance);
                NotificationManager manager = getSystemService(NotificationManager.class);
                channel.setDescription(description);
                manager.createNotificationChannel(channel);

            }
        }


}
