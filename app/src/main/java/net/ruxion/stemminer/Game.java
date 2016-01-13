package net.ruxion.stemminer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Game extends SurfaceView implements Runnable
{
	private Thread thread;
    private SurfaceHolder holder;
    private Paint paint;
    private Canvas canvas;
    private int height;
    private int width;
    private int xInterval;
    private int yInterval;
    private int xIntervalPos;

    private int score = 0;

    private boolean running;

    private List<Asteroid> asteroids = new ArrayList<>();
	private Ship ship = new Ship();

	private int asteroidsLeft;
	private Random r = new Random();
	private Date asteroidHold;

    private boolean started = false;

    public Game (Context context)
    {
        super(context);

        running = false;
        paint = new Paint();
        holder = getHolder();

        xIntervalPos = 0;

        asteroidsLeft = MainActivity.stage * 8;
    }

    public void stop()
    {
        running = false;
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight)
    {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        this.width = width;
        this.height = height;

        xInterval = (width  / 40) - 5;
        yInterval = (height / 100);

        started = true;
    }

    public void start()
    {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run ()
    {
		asteroidHold = new Date();

        while(!started);

        while (running)
        {
            updateGame();
            drawGame();
        }

        MainActivity.upStage();

        System.out.println(MainActivity.stage);
    }

    public void updateGame ()
    {
        handleShip();
		handleAsteroids();
        handleHitBox();
    }

	private void handleShip()
	{
		if(ship.movingRight())
		{
			if(xIntervalPos != 40)
			{
				xIntervalPos += 1;
				ship.setX(xInterval * xIntervalPos);
			}
		}
		else if(ship.movingLeft())
		{
			if(xIntervalPos != 0)
			{
				xIntervalPos -= 1;
				ship.setX(xInterval * xIntervalPos);
			}
		}
	}

	private void handleAsteroids()
	{
        int speed = 500;

        if(!(speed <= 30))
            speed = (500 - (MainActivity.stage * 50));

        if((new Date().getTime() - asteroidHold.getTime()) > speed )
        {
            if (asteroidsLeft != 0)
            {
                asteroids.add(new Asteroid(r.nextInt(xInterval * 40), 5));
                asteroidsLeft--;
            }
            asteroidHold = new Date();
        }

		for(int i = 0; i < asteroids.size();)
		{
			Asteroid a = asteroids.get(i);

			if(a.getyIntervalPos() != 100)
			{
				a.setyIntervalPos(a.getyIntervalPos()+1);
				a.setY(yInterval * a.getyIntervalPos());
				i++;
			}
			else
			{
				asteroids.remove(i);
				score++;
                if(asteroids.size() == 1)
                    MainActivity.act.quiz();
			}
		}
	}

	private void handleHitBox()
	{
		Rec playerHitBox = new Rec(new int[]{ ship.getX(), (int)(height * .88)}, new int[]{ship.getX()+(int)(width*.128), height}  );

		for(Asteroid a : asteroids)
		{
			Rec asteroidHitBox = new Rec(new int[]{ a.getX()+20, a.getY()}, new int[]{a.getX()+85, a.getY()+82}             );

            if(Util.contains(playerHitBox.topLeft[0], playerHitBox.topLeft[1], playerHitBox.bottomRight[0], playerHitBox.bottomRight[1], asteroidHitBox.bottomRight[0], asteroidHitBox.bottomRight[1])
            || Util.contains(playerHitBox.topLeft[0], playerHitBox.topLeft[1], playerHitBox.bottomRight[0], playerHitBox.bottomRight[1], asteroidHitBox.topLeft[0], asteroidHitBox.bottomRight[1]))
            {
                MainActivity.act.lose();
            }

		}
	}

    public void drawGame ()
    {
        if (holder.getSurface().isValid())
        {
            canvas = holder.lockCanvas();

            canvas.drawBitmap(MainActivity.space, 0, 0, paint);

            canvas.drawBitmap(MainActivity.spaceship, ship.getX(), (int) (height * .85), paint);
            paint.setTextSize(70);
            paint.setColor(Color.GREEN);
            canvas.drawText(score+"", 100, 100, paint);

            for(int i = 0; i < asteroids.size(); i++)
            {
                Asteroid a = asteroids.get(i);
                canvas.drawBitmap(MainActivity.asteroid, a.getX(), a.getY(), paint);
            }

            holder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean onTouchEvent (MotionEvent event)
    {

        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if(Util.contains(0, 0, (int)(width*.5), height, (int)event.getX(), (int)event.getY()))
                    ship.setMoving(false);
                else
                    ship.setMoving(true);
                break;
            case MotionEvent.ACTION_UP:
                ship.stopMoving();
                break;
        }

        return true;
    }

}

class Ship
{
    private boolean right = false;
    private boolean left  = false;
    private int x = 0;

    public void setX(int x)
    {
        this.x = x;
    }

    public void stopMoving()
    {
        right = left = false;
    }

    public void setMoving(boolean leftright)
    {
        stopMoving();

        if(leftright)
            right = true;
        else
            left = true;
    }

    public int getX()
    {
        return x;
    }

    public boolean movingRight()
    {
        return right;
    }

    public boolean movingLeft()
    {
        return left;
    }

}

class Asteroid
{
    private int x;
    private int y;
    private int yIntervalPos;
    private int size;

    public Asteroid(int x, int size)
    {
        this.x = x;
        this.y = 0;
        this.size = size;
        this.yIntervalPos = 0;
    }

    public void setyIntervalPos(int yIntervalPos)
    {
        this.yIntervalPos = yIntervalPos;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getSize()
    {
        return size;
    }

    public int getyIntervalPos()
    {
        return yIntervalPos;
    }

	public String toString()
	{
		return "x="+x+", y="+y+", yIntervalPos="+yIntervalPos+", size="+size;
	}
}