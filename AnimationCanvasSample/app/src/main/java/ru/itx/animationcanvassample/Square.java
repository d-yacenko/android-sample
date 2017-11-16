package ru.itx.animationcanvassample;
import android.graphics.*;
import android.graphics.drawable.*;

public class Square extends Figure
{
	private int l;
	private Paint paint;
	
	@Override
	public void draw(Canvas canvas)
	{
		canvas.drawRect(x,y,x+l,y+l,paint);
	}

	@Override
	public void checkNextPosition(int maxWidth,int maxHeight)
	{
		if(x+dx<0 || x+l+dx>maxWidth)dx=-dx;
		if(y+dy<0 || y+l+dy>maxHeight)dy=-dy;
	}
	
	public Square(int x,int y,int l,int dx,int dy,int color){
		super(x,y,dx,dy);
		this.l=l;
		paint=new Paint();
		paint.setColor(color);
	}
	
}
