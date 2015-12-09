package net.ruxion.stemminer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.WindowManager;

public class Game extends SurfaceView implements Runnable
{
	private Thread thread;
	private static Paint paint = new Paint();
	private static int height;
	private static int width;

	private boolean startScreen;
	private boolean running;

	public Game(Context context, boolean restarted)
	{
		super(context);

		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;

		startScreen = true;
		running = false;
	}

	@Override
	public void run()
	{
		while(startScreen)
	}

	@Override
	protected void onDraw(Canvas canvas)
	{

	}

	public void pause()
	{

	}

	public void resume()
	{

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
