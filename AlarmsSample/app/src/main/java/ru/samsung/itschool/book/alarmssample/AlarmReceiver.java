package ru.samsung.itschool.book.alarmssample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class AlarmReceiver extends AppCompatActivity {
    final public static String TAG=AlarmReceiver.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_receiver);
        Log.d(TAG,"onCreate");
    }
}
