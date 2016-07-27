package com.samsung.myitschool.broadcastrecivertest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

public class Test extends BroadcastReceiver {
    Context ctx;

    public Test() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ctx=context;
        Log.d("BROADCAST", "Receiver onreceive");
        context.startService(new Intent(context, MyService.class));
    }

}
