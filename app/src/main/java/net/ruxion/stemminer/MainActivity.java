package net.ruxion.stemminer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity
{
    private Game game;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(game = new Game(this, false));
    }

    public void restartGame ()
    {
        setContentView(game = new Game(this, true));
    }

    @Override
    protected void onResume ()
    {
        super.onResume();

        game.resume();
    }

    @Override
    protected void onPause ()
    {
        super.onPause();

        game.pause();
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
