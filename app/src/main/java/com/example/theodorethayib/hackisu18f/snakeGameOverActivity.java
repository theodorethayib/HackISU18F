package com.example.theodorethayib.hackisu18f;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class snakeGameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_snake_game_over);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);

        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            //        @Override
            public void run() {
                TextView welcome2 = findViewById(R.id.welcome2);
                welcome2.setText("Would you like to play again?");
                //start your activity here
            }

        }, 2000L);

        Handler nHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            //        @Override
            public void run() {
                Button button1 = findViewById(R.id.button1);
                Button button2 = findViewById(R.id.button2);

                button1.setVisibility(View.VISIBLE);
                button2.setVisibility(View.VISIBLE);
                //start your activity here
            }

        }, 3000L);


    }

    public void noButton(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void yesButton(View view) {
        Intent intent = new Intent(this, snakeGameActivity.class);
        startActivity(intent);
    }
}
