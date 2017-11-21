package ru.itx.deeplinksample;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String action = intent.getAction();
        String data = intent.getDataString();
        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            String recipeId = data.substring(data.lastIndexOf("/") + 1);
            setContentView(R.layout.activity_main_deep);
            tv=(TextView) findViewById(R.id.tv);
            tv.setText(action+"\n"+data);
        } else {
            setContentView(R.layout.activity_main);
            tv=(TextView) findViewById(R.id.tv);
            tv.setText(tv.getText().toString()+"\nrun one from this:\n" +
                    "./adb shell am start -a android.intent.action.VIEW -d \"http://www.itx.ru/about\"\n" +
                    "./adb shell am start -a android.intent.action.VIEW -d \"sample://itx\"\n+" +
                    "./adb shell am start -a android.intent.action.VIEW -d \"android-app://ru.itx.deeplinksample/http/www.itx.ru/about\"\n" +
                    "or search this links from system search\n" +
                    "or click GO button");
        }

    }

    public void goTo(View view) {
        String url = "http://www.itx.ru/about";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
