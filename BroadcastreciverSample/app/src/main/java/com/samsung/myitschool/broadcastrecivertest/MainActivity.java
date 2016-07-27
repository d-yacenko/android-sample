package com.samsung.myitschool.broadcastrecivertest;

import android.content.Context;
import android.content.Intent;
import android.media.MediaCodecInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static Context ctx;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("BROADCAST", "Activity oncreate");

        ctx=this;
        btn=(Button)findViewById(R.id.button);
        if(MyService.status){
            btn.setText("Service DOWN!");
        }
        else {
            btn.setText("Service UP!");
        }
    }

    public void click(View view) {
        if(!MyService.status){
            startService(new Intent(this, MyService.class));
            btn.setText("Service DOWN!");

        }else {
            stopService(new Intent(this, MyService.class));
            btn.setText("Service UP!");
        }
    }
}
