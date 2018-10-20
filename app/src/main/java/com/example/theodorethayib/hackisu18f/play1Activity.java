package com.example.theodorethayib.hackisu18f;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.String;
import java.util.Random;

public class play1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play1);
    }

    public void launchPlay2Activity(View view) {
        Random rand = new Random();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent = new Intent(this, play2Activity.class);
        Globals g = (Globals)getApplication();
        EditText playerNameET = (EditText) findViewById(R.id.playerNameTextView);
        String playerNameString = playerNameET.getText().toString();
        if (playerNameString.length() == 0) {
            int randomNum = rand.nextInt(4);
            Toast toast;
            switch (randomNum) {
                case 0:toast = Toast.makeText(getApplicationContext(),"Please enter a name!", Toast.LENGTH_LONG); toast.show(); break;
                case 1:toast = Toast.makeText(getApplicationContext(),"Do you really not have a name?", Toast.LENGTH_LONG); toast.show(); break;
                case 2:toast = Toast.makeText(getApplicationContext(),"I find it hard to believe that you do not have a name...", Toast.LENGTH_LONG); toast.show(); break;
                case 3:toast = Toast.makeText(getApplicationContext(),"I can do this all day, you know!", Toast.LENGTH_LONG); toast.show(); break;
            }
        }
        if (playerNameString.length() > 0) {
            g.setPlayerName(playerNameString);
            startActivity(intent);
        }
    }
}
