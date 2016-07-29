package ru.itx.sensorsample;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	TextView mTextView;
	SensorEventListener sampleListener = new SampleSensorEventListener();
	private float mAccel; // текущая Акселерация (ускорение) без гравитации
	private float mAccelCurrent; // текущая акселерация (ускорение), включая
									// гравитацию
	private float mAccelLast; // последняя акселерация (ускорение), включая
								// гравитацию

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTextView = (TextView) findViewById(R.id.sensorText);
		mAccel = 0.00f;
		mAccelCurrent = SensorManager.GRAVITY_EARTH;
		mAccelLast = SensorManager.GRAVITY_EARTH;
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		((SensorManager) getSystemService(SENSOR_SERVICE))
				.unregisterListener(sampleListener);
	}

	@Override
	protected void onResume() {
		super.onResume();
		SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		Sensor s = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		String text = s != null ? s.toString() : "No sensor";
		mTextView.setText(text);
		text += "\nVendor: " + s.getVendor() + "\nVersion: " + s.getVersion()
				+ "\nRange: " + s.getMaximumRange() + "\nResolution: "
				+ s.getResolution() + "\nElectrical power: " + s.getPower();
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
		mTextView.setText(text);
		((SensorManager) getSystemService(SENSOR_SERVICE)).registerListener(
				sampleListener, s, SensorManager.SENSOR_DELAY_NORMAL);
	}

	class SampleSensorEventListener implements SensorEventListener {
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			float x = event.values[0];
			float y = event.values[1];
			float z = event.values[2];
			mAccelLast = mAccelCurrent;
			mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
			float delta = mAccelCurrent - mAccelLast;
			mAccel=mAccel * 0.9f + delta;
			if(mAccel>3) {
				mTextView.setText("Тряска");
				mAccel = 0.00f;
			}
		}
	}
}