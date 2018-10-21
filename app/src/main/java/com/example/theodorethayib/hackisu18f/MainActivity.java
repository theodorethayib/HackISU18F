package com.example.theodorethayib.hackisu18f;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);

        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);

        Globals g = (Globals)getApplication();
        String helloTextString = g.getPlayerName();
        TextView helloText = findViewById(R.id.welcome1);
        Boolean alreadyWatchedB = g.getAlreadyWatched();
        if (helloTextString == null) {
            helloText.setText("Hello!");
        } else {
            helloText.setText("Hello, " + helloTextString + "!");
        }


        if (alreadyWatchedB == false) {


            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {

                //        @Override
                public void run(){
                    TextView welcome2 = findViewById(R.id.welcome2);
                    welcome2.setText("Welcome to the game!");
                    //start your activity here
                }

            }, 2000L);

            Handler nHandler = new Handler();
            mHandler.postDelayed(new Runnable() {

                //        @Override
                public void run(){
                    Button button1 = findViewById(R.id.button1);
                    Button button2 = findViewById(R.id.button2);
                    Button button3 = findViewById(R.id.button3);

                    button1.setVisibility(View.VISIBLE);
                    button2.setVisibility(View.VISIBLE);
                    button3.setVisibility(View.VISIBLE);
                    //start your activity here
                }

            }, 4000L);
            g.setAlreadyWatched(true);
        } else {
            TextView welcome2 = findViewById(R.id.welcome2);
            welcome2.setText("Welcome to the game!");
            button1 = findViewById(R.id.button1);
            button2 = findViewById(R.id.button2);
            button3 = findViewById(R.id.button3);

            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
            button3.setVisibility(View.VISIBLE);
        }




    }
    public void launchPlay1Activity(View view) {
        Intent intent = new Intent(this, arrowGameActivity.class);
        startActivity(intent);
    }
    public void launchExtraActivity(View view) {
        Intent intent = new Intent(this, play1Activity.class);
        startActivity(intent);
    }

    public void launchTempActivity(View view) {
        Intent intent = new Intent(this, snakeGameActivity.class);
        startActivity(intent);
    }
}
