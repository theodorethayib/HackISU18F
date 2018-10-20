package com.example.theodorethayib.hackisu18f;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class play3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play3);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);

        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            //        @Override
            public void run(){
                TextView welcome2 = findViewById(R.id.welcome2);
                welcome2.setText("Are you ready to play?");
                //start your activity here
            }

        }, 2000L);

        Handler nHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            //        @Override
            public void run(){
                Button button1 = findViewById(R.id.button1);
                Button button2 = findViewById(R.id.button2);

                button1.setVisibility(View.VISIBLE);
                button2.setVisibility(View.VISIBLE);
                //start your activity here
            }

        }, 4000L);


    }
}
