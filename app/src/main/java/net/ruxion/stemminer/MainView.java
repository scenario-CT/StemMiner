package net.ruxion.stemminer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class MainView extends View
{
	private static Paint paint = new Paint();
	private static int height;
	private static int width;


	public MainView(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;

		System.out.println(width+"x"+height);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{

		Drawable background = getResources().getDrawable(R.drawable.space);
		background.setBounds(0, 0, width, height);
		background.draw(canvas);



	}

	class Ship
	{
		boolean on;
		int postion;

		public Ship()
		{
			on = false;
			postion = 0;
		}

		public void draw(Canvas canvas)
		{
			Drawable ship = getResources().getDrawable(R.drawable.spaceship);
			ship.setBounds(0, 0, width, height);
			ship.draw(canvas);


		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		return false;
	}
}
