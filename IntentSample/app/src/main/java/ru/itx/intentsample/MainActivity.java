package ru.itx.intentsample;

import android.app.*;
import android.os.*;
import android.view.*;
import android.content.*;

public class MainActivity extends Activity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
	
	public void goIntent1(View v) {
		Intent i=new Intent(this,ImplicitIntentActivity.class);
		startActivity(i);
	}
}
