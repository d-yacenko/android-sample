package ru.itx.notificationsample;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    // Идентификатор уведомления
    private static final int NOTIFY_ID = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goNotify(View view) {
        Context context = getApplicationContext();
        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        Resources res = context.getResources();
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentIntent(contentIntent)
                // Small icon (top status line)
                .setSmallIcon(R.drawable.star_2)
                // Big image
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.star))
                .setTicker(res.getString(R.string.notify_status_string)) // текст в строке состояния
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
//                .setProgress(100, 50, false)
                .setContentTitle(res.getString(R.string.notify_header))
                .setContentText(res.getString(R.string.notify_msg)); // notification text
        Notification notification;
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
            notification = builder.getNotification();
        } else{
            notification = builder.build();
        }
        notification.defaults = Notification.DEFAULT_SOUND |Notification.DEFAULT_VIBRATE;
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFY_ID, notification);
    }

    public void cancelNotify(View view) {
        Context context = getApplicationContext();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFY_ID);
    }
}
