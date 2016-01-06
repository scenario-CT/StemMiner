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
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends SurfaceView implements Runnable
{
    private Thread thread;
    private SurfaceHolder holder;
    private Paint paint;
    private Canvas canvas;
    private Bitmap space;
    private Bitmap spaceship;
    private Bitmap asteroid;
    private int height;
    private int width;
    private int xInterval;
    private int yInterval;
    private int xIntervalPos;

    private int score = 0;

    private boolean running;

    private List<Asteroid> asteroids = Collections.synchronizedList(new ArrayList<Asteroid>());
    private Ship ship = new Ship();

    private Timer timer = new Timer();
    private int length = 1000;

	private boolean started;

    public Game (Context context)
    {
        super(context);

        running = false;
        paint = new Paint();
        holder = getHolder();
		started = false;

        xIntervalPos = 0;

		System.out.println("1");
    }

    public void stop()
    {
        timer.cancel();
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

        space = Util.decodeSampledBitmapFromResource(getResources(), R.drawable.space, width, height);
        spaceship = Util.decodeSampledBitmapFromResource(getResources(), R.drawable.spaceship, 0, 50);
        asteroid = Util.decodeSampledBitmapFromResource(getResources(), R.drawable.asteroid, 0, 20);

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
		System.out.println("3");

        while (!started) {}

        timer.schedule(new Spawner(), 0, 1000);

        while (running)
        {
            updateGame();
            drawGame();
        }
    }

    class Spawner extends TimerTask
    {
        private int asteroidsLeft = 8;
        private Random r = new Random();

        public void run ()
        {
            if (asteroidsLeft != 0)
            {
                asteroids.add(new Asteroid(r.nextInt(xInterval * 40), 5));
                asteroidsLeft--;
            }
//            else
//            {
//                timer.purge();
//                timer.cancel();
//                timer = new Timer();
//
//                if(length != 300)
//                {
//                    length -= 100;
//                    timer.schedule(new Spawner(), 0, length);
//                }
//                else
//                {
//                    timer.schedule(new Spawner(), 0, 300);
//                }
//            }
        }
    }

    public void updateGame ()
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
            }
        }

        Rec playerHitBox = new Rec(new int[]{ ship.getX(), (int)(height * .88)}, new int[]{ship.getX()+(int)(width*.128), height}  );

        for(Asteroid a : asteroids)
        {
            Rec asteroidHitBox = new Rec(new int[]{ a.getX()+20, a.getY()}, new int[]{a.getX()+100, a.getY()+82}             );

            if(Util.contains(playerHitBox.topLeft[0], playerHitBox.topLeft[1], playerHitBox.bottomRight[0], playerHitBox.bottomRight[1], asteroidHitBox.bottomRight[0], asteroidHitBox.bottomRight[1])
            || Util.contains(playerHitBox.topLeft[0], playerHitBox.topLeft[1], playerHitBox.bottomRight[0], playerHitBox.bottomRight[1], asteroidHitBox.topLeft[0], asteroidHitBox.bottomRight[1]))
            {
                MainActivity.act.lose();
            }

        }

		if(asteroids.size() == 0)
		{
			MainActivity.act.quiz();
		}
    }

    public void drawGame ()
    {
        if (holder.getSurface().isValid())
        {
            canvas = holder.lockCanvas();

            canvas.drawBitmap(space, 0, 0, paint);

            canvas.drawBitmap(spaceship, ship.getX(), (int) (height * .85), paint);
            paint.setTextSize(70);
            paint.setColor(Color.GREEN);
            canvas.drawText(score+"", 100, 100, paint);

            for(int i = 0; i < asteroids.size(); i++)
            {
                Asteroid a = asteroids.get(i);
                canvas.drawBitmap(asteroid, a.getX(), a.getY(), paint);
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
                {
                    ship.setMoving(false);
                }
                else
                {
                    ship.setMoving(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                ship.stopMoving();
                break;
        }

        return true;
    }

    public void printState()
    {
        System.out.println("\nscore = "+(score-2));
        for(Asteroid a : asteroids)
        {
            System.out.println("\nsize = "+a.getSize());
            System.out.println("x = "+a.getX());
            System.out.println("y = "+a.getY());
            System.out.println("yIntPos = "+a.getyIntervalPos()+"\n");
        }
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
}