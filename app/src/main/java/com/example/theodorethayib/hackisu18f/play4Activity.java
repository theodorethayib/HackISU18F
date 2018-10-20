package com.example.theodorethayib.hackisu18f;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class play4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play4);
    }

    public void submitButton(View view) {
        EditText inputNum = (EditText) findViewById(R.id.nameLengthInput);
        String value = inputNum.getText().toString();
        int inputNumInt = Integer.parseInt(value);
        Globals g = (Globals)getApplication();
        int playerNameLength = g.getPlayerName().length();
        if (inputNumInt == 8 && playerNameLength != 8) {
            TextView info1 = findViewById(R.id.info1);
            info1.setText("Ha ha ha...");
            TextView info2 = findViewById(R.id.info2);
            info2.setText("Very funny...");
        } else if (inputNumInt == playerNameLength) {
            Intent intent = new Intent(this, play4_1Activity.class);
            startActivity(intent);
        }
        else {
            TextView info1 = findViewById(R.id.info1);
            info1.setText("Hmm...");
            TextView info2 = findViewById(R.id.info2);
            info2.setText("That's not quite right!");
        }
    }
}
