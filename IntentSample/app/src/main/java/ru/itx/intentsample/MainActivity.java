package ru.itx.intentsample;

import android.app.*;
import android.os.*;
import android.view.*;
import android.content.*;
import android.widget.*;

public class MainActivity extends Activity 
{
	EditText message,query;
	TextView result;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		message=(EditText) findViewById(R.id.message);
		query=(EditText)findViewById(R.id.query);
		result=(TextView)findViewById(R.id.result);
    }
	
	public void goExplicitIntent(View v) {
		Intent i=new Intent(this,ExplicitIntentActivity.class);
		startActivity(i);
	}
	
	public void goImplicitIntent(View v){
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, message.getText().toString());
		sendIntent.setType("text/plain");
		if (sendIntent.resolveActivity(getPackageManager()) != null) {
			startActivity(sendIntent);
		}
	}
	
	public void goIntentForResult(View v){
		Intent intent = new Intent(this, ResultIntentActivity.class);
		intent.putExtra("query",query.getText().toString());
		startActivityForResult(intent, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) {return;}
		String sresult = data.getStringExtra("result");
		result.setText("ответ на запрос: " + sresult);
	}
}
