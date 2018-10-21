package com.example.theodorethayib.hackisu18f;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.Random;


public class snakeGameActivity extends AppCompatActivity {
    SnakeView snakeView;
    public enum Direction {UP, RIGHT, DOWN, LEFT};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
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



    class SnakeView extends SurfaceView implements Runnable {
        private Thread m_Thread = null;

        private volatile boolean m_Playing;

        private Canvas m_Canvas;
        private SurfaceHolder m_Holder;
        private Paint m_Paint;
        private Paint m_PaintFood;
        private Context m_context;

        private int rColor;
        private int gColor;
        private int bColor;


        private Direction m_Direction = Direction.RIGHT;

        private int m_Score;


        private long m_NextFrameTime;
        private final long FPS = 10;

        private int[] m_SnakeXs;
        private int[] m_SnakeYs;

        private int m_ScreenWidth;
        private int m_ScreenHeight;

        private int m_SnakeLength;

        private int m_MouseX;
        private int m_MouseY;

        private int m_BlockSize;

        private final int NUM_BLOCKS_WIDE = 10;
        private int m_NumBlocksHigh;


        public SnakeView (Context context, Point size) {
            super(context);

            m_context = context;

            m_ScreenWidth = size.x;
            m_ScreenHeight = size.y;

            m_BlockSize = m_ScreenWidth / NUM_BLOCKS_WIDE;
            m_NumBlocksHigh = m_ScreenHeight / m_BlockSize;

            m_Holder = getHolder();
            m_Paint = new Paint();
            m_PaintFood = new Paint();

            m_SnakeXs = new int[200];
            m_SnakeYs = new int[200];

            startGame();
        }

        @Override
        public void run() {
            while (m_Playing) {
                if (checkForUpdate()) {
                    updateGame();
                    drawGame();
                }
            }
        }

        public void pause() {
            m_Playing = false;
            try {
                m_Thread.join();
            }
            catch (InterruptedException e) {
            }
        }

        public void resume() {
            m_Playing = true;
            m_Thread = new Thread(this);
            m_Thread.start();
        }

        public void startGame() {
            m_SnakeLength = 1;
            m_SnakeXs[0] = NUM_BLOCKS_WIDE / 2;
            m_SnakeYs[0] = m_NumBlocksHigh / 2;

            rColor = 100;
            gColor = 100;
            bColor = 250;

            spawnFood();
            m_Score = 0;
            m_NextFrameTime = System.currentTimeMillis();
        }

        public void spawnFood() {
            Random rand = new Random();
            int tempX = rand.nextInt((NUM_BLOCKS_WIDE - 1) + 1);
            int tempY = rand.nextInt((m_NumBlocksHigh - 1) + 1);
//        while (m_SnakeXs[0] == tempX && m_SnakeYs[0] == tempY) {
//            tempX = rand.nextInt(NUM_BLOCKS_WIDE - 1) + 1;
//            tempY = rand.nextInt(m_NumBlocksHigh - 1) + 1;
//        }
            m_MouseX = tempX;
            m_MouseY = tempY;
        }

        public void eatFood() {
            if (gColor <= 220) {
                gColor += 30;
                bColor -= 30;
            } else {
                gColor -= 30;
                bColor += 30;
            }
            m_Score ++;
            m_SnakeLength ++;
            spawnFood();
        }

        public void moveSnake() {
            for (int i = m_SnakeLength; i > 0; i--) {
                m_SnakeXs[i] = m_SnakeXs[i - 1];
                m_SnakeYs[i] = m_SnakeYs[i - 1];
            }

            switch (m_Direction) {
                case UP: m_SnakeYs[0] --; break;
                case DOWN: m_SnakeYs[0] ++; break;
                case LEFT: m_SnakeXs[0] --; break;
                case RIGHT: m_SnakeXs[0] ++; break;
            }
        }

        private boolean detectDeath() {
            boolean dead = false;

            if(m_SnakeXs[0] == -1) {
                dead = true;
            }if(m_SnakeXs[0] >= NUM_BLOCKS_WIDE) {
                dead = true;
            }if(m_SnakeYs[0] == -1) {
                dead = true;
            }if(m_SnakeYs[0] >= m_NumBlocksHigh) {
                dead = true;
            }

            for (int i = m_SnakeLength - 1; i > 0; i--) {
                if ((i >= 1) && (m_SnakeXs[0] == m_SnakeXs[i]) && (m_SnakeYs[0] == m_SnakeYs[i])) {
                    dead = true;
                }
            }
            return dead;
        }

        public void updateGame() {
            if (m_SnakeXs[0] == m_MouseX && m_SnakeYs[0] == m_MouseY) {
                eatFood();
            }

            moveSnake();

            if (detectDeath()) {
                gameOver();
//                startGame();
            }
        }

        public void gameOver() {
            m_Playing = false;
            clearScreen();
            Intent intent = new Intent(getContext(), snakeGameOverActivity.class);
            startActivity(intent);
        }

        private void clearScreen() {
            m_Canvas = m_Holder.lockCanvas();
            m_Canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            m_Holder.unlockCanvasAndPost(m_Canvas);
        }

        public void drawGame() {
            if (m_Holder.getSurface().isValid()) {
                m_Canvas = m_Holder.lockCanvas();

                m_Canvas.drawColor(Color.argb(255, 68, 68, 68));
//            m_Canvas.drawColor(444);

//                m_Paint.setColor(Color.argb(255, 156, 156, 156));
                m_Paint.setColor(Color.argb(255, rColor, gColor, bColor));
                m_PaintFood.setColor(Color.argb(100, 200, 200, 200));


                m_Paint.setTextSize(200);
                m_Canvas.drawText("Score:" + m_Score, (m_ScreenWidth / 2) - 400, 200, m_Paint);

                for (int i = 0; i < m_SnakeLength; i++) {
                    m_Canvas.drawRect(m_SnakeXs[i] * m_BlockSize,
                            m_SnakeYs[i] * m_BlockSize,
                            (m_SnakeXs[i] * m_BlockSize) + m_BlockSize,
                            (m_SnakeYs[i] * m_BlockSize) + m_BlockSize,
                            m_Paint);
                }

                m_Canvas.drawRect(m_MouseX * m_BlockSize, m_MouseY * m_BlockSize,
                        (m_MouseX * m_BlockSize) + m_BlockSize,
                        (m_MouseY * m_BlockSize) + m_BlockSize,
                        m_PaintFood);

                m_Holder.unlockCanvasAndPost(m_Canvas);
            }
        }

        public boolean checkForUpdate() {
            if(m_NextFrameTime <= System.currentTimeMillis()) {
                m_NextFrameTime = System.currentTimeMillis() + 500;

                return true;
            }
            return false;
        }

        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {

            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP:
                    if (motionEvent.getX() >= m_ScreenWidth * 3 / 4) {
                        m_Direction = Direction.RIGHT;
                    } else if (motionEvent.getX() <= m_ScreenWidth / 4) {
                        m_Direction = Direction.LEFT;
                    } else {
                        if (motionEvent.getY() > m_ScreenHeight * 2 / 3) {
                            m_Direction = Direction.DOWN;
                        }
                        else {
                            m_Direction = Direction.UP;
                        }
                    }
            }

            return true;
        }

    }

}
