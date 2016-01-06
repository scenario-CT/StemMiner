package net.ruxion.stemminer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class Quiz extends AppCompatActivity
{
    private Question ques;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        ques = Question.getRandom();

        TextView view = (TextView) findViewById(R.id.textView2);
        view.setText(ques.getQuestion());

        Button one = (Button) findViewById(R.id.button);
        one.setText(ques.getOne());
        one.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                if(ques.getRight() == 1)
                    MainActivity.act.restart();
                else
                    MainActivity.act.lose();
            }
        });

        Button two = (Button) findViewById(R.id.button2);
        two.setText(ques.getTwo());
        two.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                if(ques.getRight() == 2)
                    MainActivity.act.restart();
                else
                    MainActivity.act.lose();
            }
        });

        Button three = (Button) findViewById(R.id.button3);
        three.setText(ques.getThree());
        three.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                if(ques.getRight() == 3)
                    MainActivity.act.restart();
                else
                    MainActivity.act.lose();
            }
        });

        Button four = (Button) findViewById(R.id.button4);
        four.setText(ques.getFour());
        four.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                if(ques.getRight() == 4)
                    MainActivity.act.restart();
                else
                    MainActivity.act.lose();
            }
        });
    }

}

enum Question
{
    q("What is C#", "Name of OS", "Programming Language", "Unit of measure", "Variable of speed", (byte)2),
    w("What is the unit for density?", "g/cm", "g/cm^3", "g/ft^3", "g/ft", (byte)2);

    private String question;
    private String one;
    private String two;
    private String three;
    private String four;
    private byte right;

    private Question(String question, String one, String two, String three, String four, byte right)
    {
        this.question = question;
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
        this.right = right;
    }

    public static Question getRandom()
    {
        Random r = new Random();
        return Question.values()[r.nextInt(Question.values().length)];
    }

    public String getQuestion()
    {
        return question;
    }

    public String getOne()
    {
        return one;
    }

    public String getTwo()
    {
        return two;
    }

    public String getThree()
    {
        return three;
    }

    public String getFour()
    {
        return four;
    }

    public byte getRight()
    {
        return right;
    }
}
