package net.ruxion.stemminer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
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
    private int height;
    private int width;
    private int xIntervals;
    private int yIntervals;

    private boolean running;

    private ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
    private Ship ship = new Ship();

    private Timer timer = new Timer();

    public Game (Context context)
    {
        super(context);

        running = false;
        paint = new Paint();
        holder = getHolder();
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight)
    {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        this.width = width;
        this.height = height;

        xIntervals = (int) (width  *.5);
        yIntervals = (int) (height *.5);

        space = Util.decodeSampledBitmapFromResource(getResources(), R.drawable.space, width, height);
        spaceship = Util.decodeSampledBitmapFromResource(getResources(), R.drawable.spaceship, 0, 50);
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
        timer.schedule( new TimerTask()
        {
            public void run()
            {

            }
        }, 0, 60*1000);

        while (running)
        {
            updateGame();

            drawGame();
        }
    }

    public void updateGame ()
    {
        if(ship.movingRight())
        {
            ship.setX(1);
        }
        else if(ship.movingLeft())
        {

        }
    }

    public void drawGame ()
    {
        if (holder.getSurface().isValid())
        {
            canvas = holder.lockCanvas();

            canvas.drawBitmap (space, 0, 0, paint);

            canvas.drawBitmap(spaceship, ship.getX(), (int)(height*.85), paint);

            holder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean onTouchEvent (MotionEvent event)
    {

        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                ship.setMoving(true);
                break;
            case MotionEvent.ACTION_BUTTON_RELEASE:
                ship.stopMoving();
                break;
        }

        return false;
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
    private int size;

    public Asteroid(int x, int size)
    {
        this.x = x;
        this.y = 0;
        this.size = size;
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
}