package com.example.theodorethayib.hackisu18f;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void launchPlay1Activity(View view) {
        Intent intent = new Intent(this, play1Activity.class);
        startActivity(intent);
    }
    public void launchSettingsActivity(View view) {
        Intent intent = new Intent(this, settingsActivity.class);
        startActivity(intent);
    }

    public void launchTempActivity(View view) {
        Intent intent = new Intent(this, snakeGameActivity.class);
        startActivity(intent);
    }
}
