package com.example.theodorethayib.hackisu18f;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Random;

public class play3_1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play3_1);


    }

    public void yesButton(View view) {
        Intent intent = new Intent(this, play4Activity.class);
        startActivity(intent);
    }

    public void noButton(View view) {
        Globals g = (Globals)getApplication();
        Random rand = new Random();
        int randomNum2 = rand.nextInt(100);
        if (randomNum2 == 33) {
            Intent intent = new Intent(this, play3_2Activity.class);
            startActivity(intent);
        }
        TextView mainText = findViewById(R.id.welcome3);
//        mainText.setText("Hello, " + helloTextString);
        int randomNum = rand.nextInt(5);
        switch(randomNum) {
            case 0: mainText.setText("Are you sure you don't want to play?"); break;
            case 1: mainText.setText("Are you kidding me???"); break;
            case 2: mainText.setText("Don't forget to subscribe to Cat Facts in the settings menu!"); break;
            case 3: mainText.setText("You should click the yes button..."); break;
            case 4: mainText.setText("This is not that hard..."); break;
        }
    }
}
