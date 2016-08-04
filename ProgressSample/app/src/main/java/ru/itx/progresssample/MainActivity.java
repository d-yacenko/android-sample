package ru.itx.progresssample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	ProgressBar myProgressBar,myProgressBar1,myProgressBar2;
	int myProgress = 0,myProgress1 = 0,myProgress2 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myProgressBar = (ProgressBar) findViewById(R.id.progressBarHorizontal);
        myProgressBar.setMax(100);
        progressTimer pt=new progressTimer(100000,500);
        myProgressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        pt.start();
		new Thread(myThread).start();
		myProgressBar2 = (ProgressBar) findViewById(R.id.progressBar3);
		myProgressBar2.setProgressDrawable(getResources().getDrawable(R.drawable.verticalprogressbar));
        myProgressBar2.setMax(100);
     
    }
    
    public void clickplus(View v){
    	
    	if(myProgress2 >= 50) myProgressBar2.setProgressDrawable(getResources().getDrawable(R.drawable.verticalprogressbar2));
    	if(myProgress2 < 100) {myProgress2+=10;myProgressBar2.setProgress(myProgress2);}
    	
    }
    public void clickminus(View v){
    	
    	if(myProgress2 < 50) myProgressBar2.setProgressDrawable(getResources().getDrawable(R.drawable.verticalprogressbar));
    	if(myProgress2 > 0) {myProgress2-=10;myProgressBar2.setProgress(myProgress2);}
    	
    }

    class progressTimer extends CountDownTimer {
    	public progressTimer(long millisInFuture, long countDownInterval) {
    		super(millisInFuture, countDownInterval);
		}
    	
		@Override
		public void onTick(long millisUntilFinished) {
			myProgressBar.setProgress(++myProgress);
			
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			
		}
    	
    }
    private Runnable myThread = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (myProgress < 100) {
				try {
					myHandle.sendMessage(myHandle.obtainMessage());
					Thread.sleep(1000);
				} catch (Throwable t) {
				}
			}
		}

		Handler myHandle = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				myProgress1++;
				myProgressBar1.setProgress(myProgress);
			}
		};
	};
	

}
