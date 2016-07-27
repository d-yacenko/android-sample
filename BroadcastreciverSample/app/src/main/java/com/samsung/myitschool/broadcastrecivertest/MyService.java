package com.samsung.myitschool.broadcastrecivertest;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by d.yacenko on 18.04.16.
 */
public class MyService extends Service {
    public static boolean status;

    public void onCreate() {
        super.onCreate();
        status=true;
        Log.d("BROADCASTRECIVER", "Service start oncreate");
        CDT cdt=new CDT(Long.MAX_VALUE,5000);
        cdt.start();
    }

    @Override
    public void onDestroy() {
        status=false;
        Log.d("BROADCASTRECIVER", "Service ondestroy");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    class CDT extends CountDownTimer {
        public CDT(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
                Log.d("BROADCASTRECIVER", "Service live.");
        }

        @Override
        public void onFinish() {

        }
    }

}
