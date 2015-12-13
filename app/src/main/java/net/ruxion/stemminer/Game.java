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
    private SurfaceHolder holder;
    private Paint paint;
    private Canvas canvas;
    private int height;
    private int width;

    private boolean startScreen;
    private boolean running;

    public Game (Context context, boolean restarted)
    {
        super(context);

        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        startScreen = true;
        running = false;
        paint = new Paint();
        holder = getHolder();
    }

    @Override
    public void run ()
    {
        while (startScreen)
        {
            updateStart();

            drawStart();
        }

        while (running)
        {
            updateGame();

            drawGame();
        }
    }

    public void updateStart ()
    {

    }

    public void drawStart ()
    {
        if (holder.getSurface().isValid())
        {
            canvas = holder.lockCanvas();

            Drawable background = getResources().getDrawable(R.drawable.space);
            background.setBounds(0, 0, width, height);
            background.draw(canvas);

            holder.unlockCanvasAndPost(canvas);
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

            Drawable background = getResources().getDrawable(R.drawable.space);
            background.setBounds(0, 0, width, height);
            background.draw(canvas);

            Drawable ship = getResources().getDrawable(R.drawable.spaceship);
            ship.setBounds(0, 0, width, height);
            ship.draw(canvas);

            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause ()
    {
        running = false;
        try
        {
            thread.join();
        } catch (InterruptedException e)
        {
            Log.e("Error:", "joining thread caused exception:");
        }
    }

    public void resume ()
    {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public boolean onTouchEvent (MotionEvent event)
    {
        if (startScreen)
        {
            if (event.getAction() == MotionEvent.ACTION_DOWN)
            {
                switch (StartScreenButton.getButtonPressed(event.getX(), event.getY()))
                {
                    case instructions:

                        break;

                    case startButton:
                        startScreen = false;
                        break;
                }
            }
        }

        if (running)
        {

        }

        return false;
    }

    enum StartScreenButton
    {
        startButton(0, 0, 0, 0), instructions(0, 0, 0, 0);

        private StartScreenButton (int startX, int startY, int endX, int endY) //keeping in mind they are vectors
        {

        }

        public static StartScreenButton getButtonPressed (float x, float y)
        {
            return startButton;
        }
    }
}
