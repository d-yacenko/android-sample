package ru.itx.dialogsample;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	String name;
	TextView addName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addName=(TextView)findViewById(R.id.addName);
	}
	public void click(View V){
		Toast.makeText(this, "здрасьте", Toast.LENGTH_SHORT).show();
		AlertDialog.Builder builder = new AlertDialog.Builder(
				MainActivity.this);
		final Context context = this;
		// get the layout inflater
		LayoutInflater inflater = LayoutInflater.from(this);
		View dialogview = inflater.inflate(R.layout.foraldial, null);
		//LayoutInflater inflater = MainActivity.this.getLayoutInflater();
		// inflate and set the layout for the dialog
		// pass null as the parent view because its going in the dialog layout
		builder.setView(dialogview)
				.setTitle("Hallo, my dear friend!")
				.setMessage("What is your name?")
				.setCancelable(false)
				.setIcon(R.drawable.alert_icon)
				// action buttons
				.setPositiveButton("commit",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								AlertDialog alertDialog=(AlertDialog)dialog;
								EditText poleaddname = (EditText) alertDialog.findViewById(R.id.fieldAddName);
								name=poleaddname.getText().toString();
								Toast.makeText(context,"Glad to see you "+ name	+ "!", Toast.LENGTH_SHORT).show();
								addName.setText(name);
								addName.setClickable(false);
								dialog.dismiss();
							}
						})

				.show();
	}
}
