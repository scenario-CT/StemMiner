package net.ruxion.stemminer;

import android.content.Context;
import android.graphics.Bitmap;
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

    private boolean running;

    private ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
    private int shipX = 0;
    private boolean show = false;

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
        timer.schedule( new TimerTask() {
            public void run() {
                // do your work
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

    }

    public void drawGame ()
    {
        if (holder.getSurface().isValid())
        {
            canvas = holder.lockCanvas();

            canvas.drawBitmap (space, 0, 0, paint);

            canvas.drawBitmap(spaceship, shipX, (int)(height*.85), paint);

            holder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean onTouchEvent (MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {

        }

        return false;
    }

}

class Ship
{
    private boolean right = false;
    private boolean left  = false;
    private int x = 0;

    public int getX()
    {
        return x;
    }

    public void setMoving(boolean leftright)
    {
        right = left = false;

        if(leftright)
            right = true;
        else
            left = true;
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