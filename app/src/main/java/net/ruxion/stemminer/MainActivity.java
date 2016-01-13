package net.ruxion.stemminer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity
{
    private Game game;
    public static MainActivity act;

    public static Bitmap space;
    public static Bitmap spaceship;
    public static Bitmap asteroid;

    public static int stage = 1;

    public static void upStage()
    {
        stage++;
    }

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        if(space == null)
            space = Util.decodeSampledBitmapFromResource(getResources(), R.drawable.space, 1080, 1920);
        if(spaceship == null)
            spaceship = Util.decodeSampledBitmapFromResource(getResources(), R.drawable.spaceship, 0, 50);
        if(asteroid == null)
            asteroid = Util.decodeSampledBitmapFromResource(getResources(), R.drawable.asteroid, 0, 20);

        act = this;
        super.onCreate(savedInstanceState);
        setContentView(game = new Game(this));
    }

    public void restart()
    {
        if(game != null)
            game.stop();
        game = null;

        Intent myIntent = new Intent(this, this.getClass());
        startActivity(myIntent);
    }

    public void lose()
    {
        if(game != null)
            game.stop();
        Intent myIntent = new Intent(this, Lose.class);
        startActivity(myIntent);
    }

    public void quiz()
    {
        if(game != null)
            game.stop();
        Intent myIntent = new Intent(this, Quiz.class);
        startActivity(myIntent);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        game.start();
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
