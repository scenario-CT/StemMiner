package net.ruxion.stemminer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity
{
    private Game game;
    public static MainActivity act;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(game = new Game(this));
    }

    public void restart()
    {
        if(game != null)
            game.stop();
        setContentView(game = new Game(this));
    }

    public void lose()
    {
        if(game != null)
            game.stop();
        setContentView(R.layout.activity_lose);
    }

    public void quiz()
    {
        if(game != null)
            game.stop();
        setContentView(R.layout.activity_quiz);
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
