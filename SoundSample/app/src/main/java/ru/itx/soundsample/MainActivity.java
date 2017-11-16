package ru.itx.soundsample;

import android.app.*;
import android.os.*;
import android.view.*;
import android.media.*;

public class MainActivity extends Activity 
{

	boolean isPlay;
	MediaPlayer mp;

	final int MAX_STREAMS = 5;
	SoundPool sp;
	int soundIdBell;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		sp = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
	    soundIdBell = sp.load(this, R.raw.bellbc, 1);

    }

	@Override
	protected void onPause()
	{
		if (mp != null && mp.isPlaying())
			mp.pause();
		super.onPause();
	}

	@Override
	protected void onResume()
	{
		if (mp != null && isPlay)
			mp.start();
		super.onResume();
	}

	@Override
	protected void onDestroy()
	{
		if (mp != null)
		{
			mp.release();
			mp = null;
		}
		super.onDestroy();
	}



	public void play(View v)
	{
		if (isPlay) return;
		mp = MediaPlayer.create(this, R.raw.yellow);
		mp.start();
		isPlay = true;
	}

	public void play2(View v)
	{
		sp.play(soundIdBell, 1, 1, 0, 0, 1);
	}

	public void pause(View v)
	{
		if (!isPlay || mp == null) return;
		if (mp.isPlaying())
			mp.pause(); 
		else 
			mp.start();
	}
	public void stop(View v)
	{
		if (!isPlay) return;
		if (mp != null)
		{
			mp.release();
			mp = null;
		}
		isPlay = false;
	}
}
