package ru.itx.sharedpreferencessample;

import android.app.*;
import android.os.*;
import android.content.*;
import android.text.*;
import android.widget.*;
import android.view.*;

public class MainActivity extends Activity 
{
	EditText note;
	SharedPreferences sp;
	final public static String SAVED_TEXT="saved_text";
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		note=(EditText) findViewById(R.id.note);
		sp = getPreferences(MODE_PRIVATE);
		String savedText = sp.getString(SAVED_TEXT, "");
		note.setText(savedText);
		note.addTextChangedListener(new DateTextWatcher());
    }
	
	public void close(View v){
		finish();
	}
	
	class DateTextWatcher implements TextWatcher{
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
									  int after) {
		}
		@Override
		public void afterTextChanged(Editable s) {
			SharedPreferences.Editor ed = sp.edit();
			ed.putString(SAVED_TEXT, note.getText().toString());
			ed.commit();
		}
	}
}
