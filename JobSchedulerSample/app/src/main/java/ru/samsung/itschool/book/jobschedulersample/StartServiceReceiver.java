package ru.samsung.itschool.book.jobschedulersample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StartServiceReceiver extends BroadcastReceiver {
    private static final String TAG = StartServiceReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG,"onReceive");
        Util.scheduleJob(context);
    }
}
