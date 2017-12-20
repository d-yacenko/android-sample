package ru.itx.accessibleappsample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;


// see also https://github.com/googlesamples/android-BasicAccessibility
public class MainActivity extends AppCompatActivity {
    Button starButton;

    private LabeledImageView labeledImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        starButton= findViewById(R.id.star_button);
        starButton.setContentDescription(getResources().getString(R.string.star));
        labeledImageView=findViewById(R.id.imageView);
        labeledImageView.setText("test");
        labeledImageView.setFocusable(true);
        labeledImageView.setFocusableInTouchMode(true);
    }


}
