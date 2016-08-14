package ru.itx.animationcanvassample;
import android.graphics.*;

abstract public class Figure
{
	protected int x,y,dx,dy;
	
	public Figure(int x,int y,int dx,int dy){
		this.x=x;
		this.y=y;
		this.dx=dx;
		this.dy=dy;
	}
	
	public void move(){
		x+=dx;
		y+=dy;
	}
	
	abstract public void draw(Canvas canvas);
	abstract public void checkNextPosition(int maxWidth,int maxHeight);
}
