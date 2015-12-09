package net.ruxion.stemminer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class Game extends SurfaceView implements Runnable
{
	private Thread thread;
	private SurfaceHolder ourHolder;
	private Paint paint;
	private Canvas canvas;
	private int height;
	private int width;

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
		paint =  new Paint();
	}

	@Override
	public void run()
	{
		while(startScreen)
		{

		}

		while(running)
		{

		}
	}

	public void drawStart()
	{
		if(ourHolder.getSurface().isValid())
		{
			canvas = ourHolder.lockCanvas();

			ourHolder.unlockCanvasAndPost(canvas);
		}
	}

	public void drawGame()
	{
		if(ourHolder.getSurface().isValid())
		{
			canvas = ourHolder.lockCanvas();

			ourHolder.unlockCanvasAndPost(canvas);
		}
	}

	public void pause()
	{
		running = false;
		try
		{
			thread.join();
		}
		catch (InterruptedException e)
		{
			Log.e("Error:", "joining thread caused exception:");
		}
	}

	public void resume()
	{
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{

		if(startScreen)
		{

		}

		if(running)
		{

		}

		return false;
	}
}
