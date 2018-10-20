package com.example.theodorethayib.hackisu18f;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.Random;

public class arrowGameActivity extends AppCompatActivity {
    ArrowView arrowView;


    public int arrowDirInt = -1;
    public enum Direction {UP, RIGHT, DOWN, LEFT};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_arrow_game);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();

        display.getSize(size);

        arrowView = new ArrowView(this, size);



        setContentView(arrowView);
    }

    public int getArrowDirInt() {
        return arrowDirInt;
    }

    @Override
    protected void onResume() {
        super.onResume();
        arrowView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        arrowView.pause();
    }


    class ArrowView extends SurfaceView implements Runnable {
        private Thread m_Thread = null;

        private volatile boolean m_Playing;

        private Canvas m_Canvas;
        private SurfaceHolder m_Holder;
        private Paint m_Paint;
        private Paint m_PaintBG;
        private Paint m_PaintRed;
        private Paint m_PaintBlue;
        private Paint m_PaintGreen;
        private Paint m_PaintYellow;
        private Context m_context;


        private Direction m_Direction = Direction.RIGHT;

        private long m_NextFrameTime;
        private final long FPS = 20;
        private long change;

        private int stageCount;

        private int m_ScreenWidth;
        private int m_ScreenHeight;

        private int m_BlockSize;

        private int[] m_Arrows;

        private int m_Length = 10;

        private int count;


        private final int NUM_BLOCKS_WIDE = 10;
        private int m_NumBlocksHigh = 1;

        public ArrowView(Context context, Point size) {
            super(context);

            m_ScreenWidth = size.x;
            m_ScreenHeight = size.y;

            m_Holder = getHolder();
            m_Paint = new Paint();
            m_PaintBG = new Paint();
            m_PaintRed = new Paint();
            m_PaintBlue = new Paint();
            m_PaintGreen = new Paint();
            m_PaintYellow = new Paint();



            m_BlockSize = m_ScreenWidth / NUM_BLOCKS_WIDE;

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
//            drawButtons();
            m_Length = 10;
            change = 500;
            m_Arrows = new int[m_Length * 2 - 1];
            Arrays.fill(m_Arrows, -1);
            count = 0;
            m_NextFrameTime = System.currentTimeMillis();
            stageCount = 1;
        }

        public void moveArrows() {
            for (int i = m_Length - 1; i > 0; i--) {
                m_Arrows[i] = m_Arrows[i - 1];
            }
        }

        public boolean detectWrongArrow () {
        if (m_Arrows[m_Length - 1] != arrowDirInt && count >= 9) {
            return true;
        }
        return false;
        }

        public void updateGame() {
            Random rand = new Random();
            moveArrows();
            if (count >= m_Length && count < m_Length * 2) {
                m_Arrows[0] = 4;
            } else if (count < m_Length){
                m_Arrows[0] = rand.nextInt(4);
            } else {
                nextStage();
            }
            count++;
//            if (detectWrongArrow() || count == 4) {
//            if (detectWrongArrow()) {
//                gameOver();
//            }
        }

        public void nextStage() {
            m_Length *= 1.5;
            change *= .8;
            m_Arrows = new int[m_Length * 2 - 1];
            count = 0;
            Arrays.fill(m_Arrows, -1);
            stageCount++;
            updateGame();
        }

        public void gameOver() {
            m_Playing = false;
        }

//        public void drawButtons() {
//            m_PaintRed.setColor(Color.argb(255, 136, 0, 0));
//            m_PaintBlue.setColor(Color.argb(255, 0, 0, 136));
//            m_PaintGreen.setColor(Color.argb(255, 0, 136, 0));
//            m_PaintYellow.setColor(Color.argb(255, 136, 136, 0));
//
//            m_Canvas = m_Holder.lockCanvas();
//
//            m_Canvas.drawRect(0, m_ScreenHeight - 200, m_ScreenWidth / 4, m_ScreenHeight, m_PaintRed);
//
//            m_Holder.unlockCanvasAndPost(m_Canvas);
//        }

        public void drawGame() {
//            LinearLayout linearLayout = findViewById(R.id.buttonsLayout);
//            linearLayout.bringToFront();
            if (m_Holder.getSurface().isValid()) {
                m_Canvas = m_Holder.lockCanvas();

                m_Canvas.drawColor(Color.argb(255, 68, 68, 68));
//            m_Canvas.drawColor(444);


                m_PaintBG.setColor(Color.argb(255, 68, 68, 68));
                m_Paint.setColor(Color.argb(255, 136, 136, 136));
                m_PaintRed.setColor(Color.argb(255, 136, 0, 0));
                m_PaintBlue.setColor(Color.argb(255, 0, 0, 136));
                m_PaintGreen.setColor(Color.argb(255, 0, 136, 0));
                m_PaintYellow.setColor(Color.argb(255, 136, 136, 0));

                m_Paint.setTextSize(200);
                m_PaintRed.setTextSize(200);
                m_PaintBlue.setTextSize(200);
                m_PaintGreen.setTextSize(200);
                m_PaintYellow.setTextSize(200);

                m_Canvas.drawText("STAGE:" + stageCount, (m_ScreenWidth / 2) - 400, 200, m_Paint);

                m_Canvas.drawRect(0, m_ScreenHeight - (m_ScreenHeight / 8), m_ScreenWidth / 4, m_ScreenHeight, m_PaintRed);

                for (int i = 0; i < m_Arrows.length - 1; i++) {
                    switch(m_Arrows[i]) {
                        case 0: m_Canvas.drawRect(i * m_BlockSize, 4 * m_BlockSize, (i * m_BlockSize) + m_BlockSize, (4 * m_BlockSize) + m_BlockSize, m_PaintRed); break;
                        case 1: m_Canvas.drawRect(i * m_BlockSize, 4 * m_BlockSize, (i * m_BlockSize) + m_BlockSize, (4 * m_BlockSize) + m_BlockSize, m_PaintBlue); break;
                        case 2: m_Canvas.drawRect(i * m_BlockSize, 4 * m_BlockSize, (i * m_BlockSize) + m_BlockSize, (4 * m_BlockSize) + m_BlockSize, m_PaintGreen); break;
                        case 3: m_Canvas.drawRect(i * m_BlockSize, 4 * m_BlockSize, (i * m_BlockSize) + m_BlockSize, (4 * m_BlockSize) + m_BlockSize, m_PaintYellow); break;
                        case 4: m_Canvas.drawRect(i * m_BlockSize, 4 * m_BlockSize, (i * m_BlockSize) + m_BlockSize, (4 * m_BlockSize) + m_BlockSize, m_PaintBG); break;
                    }

                }
                m_Holder.unlockCanvasAndPost(m_Canvas);
            }
        }
        public boolean checkForUpdate() {
            if(m_NextFrameTime <= System.currentTimeMillis()) {
                m_NextFrameTime = System.currentTimeMillis() + change;

                return true;
            }
            return false;
        }
    }
}
