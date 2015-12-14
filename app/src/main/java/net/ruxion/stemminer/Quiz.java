package net.ruxion.stemminer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Quiz extends AppCompatActivity
{

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Button one = (Button) findViewById(R.id.button);
        one.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                MainActivity.act.lose();
            }
        });

        Button two = (Button) findViewById(R.id.button);
        two.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                MainActivity.act.restart();
            }
        });

        Button three = (Button) findViewById(R.id.button);
        three.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                MainActivity.act.lose();
            }
        });

        Button four = (Button) findViewById(R.id.button);
        four.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                MainActivity.act.lose();
            }
        });
    }

}
