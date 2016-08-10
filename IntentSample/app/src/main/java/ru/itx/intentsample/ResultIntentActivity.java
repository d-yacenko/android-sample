package ru.itx.intentsample;
import android.app.*;
import android.os.*;
import android.widget.*;
import android.view.*;
import android.content.*;

public class ResultIntentActivity extends Activity
{
	EditText result;
	TextView query;
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
		query=(TextView) findViewById(R.id.query);
		result=(EditText) findViewById(R.id.result);
		String squery=getIntent().getExtras().getString("query");
		query.setText("Запрошено "+squery);
    }
	
	public void sendBackResult(View v){
		Intent intent = new Intent();
		intent.putExtra("result", result.getText().toString());
		setResult(RESULT_OK, intent);
		finish();
	}
}
