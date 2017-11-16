package ru.itx.animationcanvassample;
import android.graphics.*;
import android.content.*;

public class Ball extends Figure
{ 
    int l;
	Bitmap ball;
	Paint paint;

	public Ball(int x,int y,int dx,int dy,Context context){
		super(x,y,dx,dy);
		paint=new Paint();
		ball=BitmapFactory.decodeResource(context.getResources(),R.drawable.ball);
		l=ball.getWidth();
	}
	
	@Override
	public void draw(Canvas canvas)
	{
		canvas.drawBitmap(ball,x,y,paint);
	}

	@Override
	public void checkNextPosition(int maxWidth, int maxHeight)
	{
		if(x+dx<0 || x+l+dx>maxWidth)dx=-dx;
		if(y+dy<0 || y+l+dy>maxHeight)dy=-dy;
	}

}
