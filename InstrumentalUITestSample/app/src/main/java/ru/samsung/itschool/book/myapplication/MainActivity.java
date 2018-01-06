package ru.samsung.itschool.book.myapplication;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
    ImageView image;
    EditText editText;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=(EditText)findViewById(R.id.editText);
        textView=(TextView)findViewById(R.id.textView);
        image = (ImageView) findViewById(R.id.image);
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View view) {
                textView.setText(editText.getText().toString());
            }
        };
        image.setOnClickListener(listener);
    }
}