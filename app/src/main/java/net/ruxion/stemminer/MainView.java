package net.ruxion.stemminer;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by townsendc on 12/2/15.
 */
public class MainView extends View
{
	public MainView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{

	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		return false;
	}
}
