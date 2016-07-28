package ru.itx.androidanimationsample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends Activity {
	ImageView ship;
	Animation shipAnim;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        ship = (ImageView) findViewById(R.id.shipView);
        shipAnim = AnimationUtils.loadAnimation(this,
        R.anim.ship_anim);
	}
    public void click(View v){
    	ship.startAnimation(shipAnim);
    }

}
