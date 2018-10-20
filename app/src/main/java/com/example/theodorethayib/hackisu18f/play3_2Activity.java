package com.example.theodorethayib.hackisu18f;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class play3_2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play3_2);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);

        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);

        final Intent intent = new Intent(this, play3_1Activity.class);

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            //        @Override
            public void run(){

                TextView welcome2 = findViewById(R.id.welcome2);
                welcome2.setText("Be that way...");
                //start your activity here
            }

        }, 2000L);

        Handler nHandler = new Handler();
        nHandler.postDelayed(new Runnable() {

            //        @Override
            public void run(){

                TextView welcome3 = findViewById(R.id.welcome3);
                welcome3.setText("You should subscribe to Cat Facts though!");
                //start your activity here
            }

        }, 4000L);

        Handler oHandler = new Handler();
        oHandler.postDelayed(new Runnable() {

            //        @Override
            public void run(){
                Button button1 = findViewById(R.id.button1);
                Button button2 = findViewById(R.id.button2);

                button1.setVisibility(View.VISIBLE);
                button2.setVisibility(View.VISIBLE);
                //start your activity here
            }

        }, 5000L);
    }

    public void launchPlay3_1Activity(View view) {
        Intent intent = new Intent(this, play3_1Activity.class);
        startActivity(intent);
    }

    public void launchSettingsActivity(View view) {
        Intent intent = new Intent(this, settingsActivity.class);
        startActivity(intent);
    }
}
