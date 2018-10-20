package com.example.theodorethayib.hackisu18f;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.app.Activity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;





public class snakeGameActivity extends AppCompatActivity {
    SnakeView snakeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake_game);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();

        display.getSize(size);
        snakeView = new SnakeView(this, size);

        setContentView(snakeView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        snakeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        snakeView.pause();
    }




}
