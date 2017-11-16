package ru.itx.animationcanvassample;
import android.graphics.*;

public class Circle extends Figure
{
	private int r;
	private Paint paint;

	@Override
	public void draw(Canvas canvas)
	{
		canvas.drawCircle(x,y,r,paint);
	}

	@Override
	public void checkNextPosition(int maxWidth,int maxHeight)
	{
		if(x-r+dx<0 || x+r+dx>maxWidth)dx=-dx;
		if(y-r+dy<0 || y+r+dy>maxHeight)dy=-dy;
	}

	public Circle(int x,int y,int r,int dx,int dy,int color){
		super(x,y,dx,dy);
		this.r=r;
		paint=new Paint();
		paint.setColor(color);
	}
}
