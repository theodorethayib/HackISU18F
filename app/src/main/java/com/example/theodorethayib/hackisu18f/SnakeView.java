package com.example.theodorethayib.hackisu18f;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v4.content.res.TypedArrayUtils;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import java.util.Arrays;
import java.lang.Object;

import java.util.Random;

class SnakeView extends SurfaceView implements Runnable {
    private Thread m_Thread = null;

    private volatile boolean m_Playing;

    private Canvas m_Canvas;
    private SurfaceHolder m_Holder;
    private Paint m_Paint;
    private Context m_context;

    public enum Direction {UP, RIGHT, DOWN, LEFT};
    private Direction m_Direction = Direction.RIGHT;

    private int m_Score;


    private long m_NextFrameTime;
    private final long FPS = 20;

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

        spawnFood();
        m_Score = 0;
        m_NextFrameTime = System.currentTimeMillis();
    }

    public void spawnFood() {
        Random rand = new Random();
        int tempX = rand.nextInt(NUM_BLOCKS_WIDE - 1) + 1;
        int tempY = rand.nextInt(m_NumBlocksHigh - 1) + 1;
//        while (m_SnakeXs[0] == tempX && m_SnakeYs[0] == tempY) {
//            tempX = rand.nextInt(NUM_BLOCKS_WIDE - 1) + 1;
//            tempY = rand.nextInt(m_NumBlocksHigh - 1) + 1;
//        }
        m_MouseX = tempX;
        m_MouseY = tempY;
    }

    public void eatFood() {
        m_SnakeLength ++;
        spawnFood();
        m_Score ++;
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
        }if(m_SnakeYs[0] >= NUM_BLOCKS_WIDE) {
            dead = true;
        }

        for (int i = m_SnakeLength - 1; i > 0; i--) {
            if ((i > 4) && (m_SnakeXs[0] == m_SnakeXs[i]) && (m_SnakeYs[0] == m_SnakeYs[i])) {
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
            startGame();
        }
    }

    public void drawGame() {
        if (m_Holder.getSurface().isValid()) {
            m_Canvas = m_Holder.lockCanvas();

            m_Canvas.drawColor(Color.argb(255, 120, 197, 87));
//            m_Canvas.drawColor(444);

            m_Paint.setColor(Color.argb(255, 255, 255, 255));
//            m_Paint.setColor(888);

            m_Paint.setTextSize(200);
            m_Canvas.drawText("Score:" + m_Score, 500, 200, m_Paint);

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
                    m_Paint);

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
                if (motionEvent.getX() >= m_ScreenWidth * 2 / 3) {
                    m_Direction = Direction.RIGHT;
                } else if (motionEvent.getX() <= m_ScreenWidth / 3) {
                    m_Direction = Direction.LEFT;
                } else {
                    if (motionEvent.getY() > m_ScreenHeight / 2) {
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