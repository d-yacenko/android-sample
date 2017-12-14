package ru.itx.themesample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goDialog(View view) {
        Intent intent=new Intent(this,Main2Activity.class);
        startActivity(intent);
    }

    public void goStyle(View view) {
        Intent intent=new Intent(this,Main3Activity.class);
        startActivity(intent);
    }

    public void goTheme(View view) {
        Intent intent=new Intent(this,Main4Activity.class);
        startActivity(intent);
    }
}
