package ru.itx.animationcanvassample;
import android.view.*;
import android.content.*;
import android.util.*;
import android.graphics.*;
import android.os.*;
import java.util.*;
import android.widget.*;
import android.graphics.drawable.*;

public class AnimationView extends View
{
	private Context context;
	private Figure figures[];
	int color;
	
	public AnimationView(Context context)
	{
		super(context);
		this.context=context;
		ColorDrawable backColor = (ColorDrawable)getBackground();
		color=backColor.getColor();
		figures=new Figure[0];
		new Timer().start();
	}
	public AnimationView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.context=context;
		ColorDrawable backColor = (ColorDrawable)getBackground();
		color=backColor.getColor();
		figures=new Figure[0];
		new Timer().start();
	}

	public void createFigures()
	{
		Random rand=new Random();
		int maxFigures=rand.nextInt(30);
		figures = new Figure[maxFigures];
		for (int i=0;i < figures.length;i++)
		{
			switch (rand.nextInt(3))
			{
				case 0:int l=rand.nextInt(100);
					figures[i] = new Square(l+rand.nextInt(getWidth()-2*l),
											   l+rand.nextInt(getHeight()-2*l),l , -15 + rand.nextInt(30),
											   -15 + rand.nextInt(30), rand.nextInt());
					break;
				case 1:int r=rand.nextInt(100);
					figures[i] = new Circle(r+rand.nextInt(getWidth()-2*r),
											   r+rand.nextInt(getHeight()-2*r), r, -15 + rand.nextInt(30),
											   -15 + rand.nextInt(30), rand.nextInt());
					break;
				case 2:int a=32;
					figures[i] = new Ball(a+rand.nextInt(getWidth()-2*a),
											a+rand.nextInt(getHeight()-2*a), -15 + rand.nextInt(30),
											-15 + rand.nextInt(30), context);
					break;
			}
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		// TODO: Implement this method
		super.onSizeChanged(w, h, oldw, oldh);
		createFigures();
	}

	
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		canvas.drawColor(color);
		for (Figure f:figures)
			f.draw(canvas);
	}

	class Timer extends CountDownTimer
	{

		@Override
		public void onTick(long p1)
		{
			for (Figure f:figures)
			{
				f.checkNextPosition(getWidth(), getHeight());
				f.move();
				invalidate();
			}
		}

		@Override
		public void onFinish()
		{
		}

		public Timer()
		{
			super(Integer.MAX_VALUE, 50);
		}
	}


}
