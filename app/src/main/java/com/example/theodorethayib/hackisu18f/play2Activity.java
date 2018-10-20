package com.example.theodorethayib.hackisu18f;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class play2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play2);

        final Intent intent = new Intent(this, play3Activity.class);

        //Set hello text to the person's name
        Globals g = (Globals)getApplication();
        String helloTextString = g.getPlayerName();
        TextView helloText = findViewById(R.id.helloTextView);
        helloText.setText("Hello, " + helloTextString);

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            //        @Override
            public void run(){

                startActivity(intent);
                //start your activity here
            }

        }, 3000L);
    }


}
