package com.example.theodorethayib.hackisu18f;

import android.app.Activity;
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
import android.view.MotionEvent;
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

        private boolean hardMode;

        private Canvas m_Canvas;
        private SurfaceHolder m_Holder;
        private Paint m_Paint;
        private Paint m_PaintX;
        private Paint m_PaintBG;
        private Paint m_PaintRed;
        private Paint m_PaintBlue;
        private Paint m_PaintGreen;
        private Paint m_PaintYellow;
        private Paint m_PaintPurple;
        private Paint m_PaintOrange;
        private Paint m_PaintCyan;
        private Paint m_PaintBrown;
        private Paint m_PaintXRed;
        private Paint m_PaintXBlue;
        private Paint m_PaintXGreen;
        private Paint m_PaintXYellow;

        private int colorDifficultyInt;
//        private Context m_context;


//        private Direction m_Direction = Direction.RIGHT;

        private long m_NextFrameTime;
        private final long FPS = 20000;
        private long change;

        private int stageCount;

        private int m_ScreenWidth;
        private int m_ScreenHeight;


        private int m_BlockSize;

        private int[] m_Arrows;

        private int m_Length = 10;

        private int count;

        private int selectedColor;

        private int livesLeft;


        private final int NUM_BLOCKS_WIDE = 9;
//        private int m_NumBlocksHigh = 1;

        public ArrowView(Context context, Point size) {
            super(context);

            m_ScreenWidth = size.x;
            m_ScreenHeight = size.y;

            m_Holder = getHolder();
            m_Paint = new Paint();
            m_PaintX = new Paint();
            m_PaintBG = new Paint();
            m_PaintRed = new Paint();
            m_PaintBlue = new Paint();
            m_PaintGreen = new Paint();
            m_PaintYellow = new Paint();
            m_PaintPurple = new Paint();
            m_PaintOrange = new Paint();
            m_PaintCyan = new Paint();
            m_PaintBrown = new Paint();
            m_PaintXRed = new Paint();
            m_PaintXBlue = new Paint();
            m_PaintXGreen = new Paint();
            m_PaintXYellow = new Paint();



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
            change = 750;
            m_Arrows = new int[m_Length * 2 - 1];
            Arrays.fill(m_Arrows, -1);
            count = 0;
            m_NextFrameTime = System.currentTimeMillis();
            stageCount = 1;
            selectedColor = -1;
            livesLeft = 3;

            if (hardMode) {colorDifficultyInt = 8;}
            else {colorDifficultyInt = 4;}
        }

        public void moveArrows() {
            for (int i = m_Length - 1; i > 0; i--) {
                m_Arrows[i] = m_Arrows[i - 1];
            }
        }

        public boolean detectWrongArrow () {
//        if (m_Arrows[9] != selectedColor && count >= 10) {
        if (m_Arrows[9] == 99 || m_Arrows[9] == -1) {
            return false;
        }else if (m_Arrows[9] != selectedColor && count > 9) {
            if (livesLeft == 1) {
                gameOver();
                return true;
            }
            else {
                livesLeft--;
                return false;
            }
        }
        return false;
        }

        public void updateGame() {
            Random rand = new Random();
            moveArrows();
//            onTouch();
            if (m_Arrows[0] == 99 && m_Arrows[11] == 99) {
                nextStage();
            }
            if (count >= m_Length && count < m_Length * 2) {
                m_Arrows[0] = 99;
            } else if (count < m_Length) {
                m_Arrows[0] = rand.nextInt(colorDifficultyInt);
            } else {
                nextStage();
            }
            count++;
            if (detectWrongArrow() || count == 99) {
                if (detectWrongArrow() && livesLeft == 0) {
                    gameOver();
                }
            }
        }

        public void nextStage() {
            m_Length *= 1.1;
            change *= .9;
            m_Arrows = new int[m_Length * 2 - 1];
            count = 0;
            Arrays.fill(m_Arrows, -1);
            stageCount++;
            if (stageCount % 3 == 1) {
                livesLeft++;
            }
            if (stageCount >= 5) {hardMode = true; colorDifficultyInt = 8; change = 700; m_Length = 10;}
            updateGame();
        }

        public void gameOver() {
            m_Playing = false;
            clearScreen();
            Intent intent = new Intent(getContext(), arrowGameOverActivity.class);
            startActivity(intent);
        }

        private void clearScreen() {
            m_Canvas = m_Holder.lockCanvas();
            m_Canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            m_Holder.unlockCanvasAndPost(m_Canvas);
        }

//        public boolean isGameOver() {
//            return gameOver;
//        }

        public void drawGame() {
            if (m_Holder.getSurface().isValid()) {
                m_Canvas = m_Holder.lockCanvas();

                m_Canvas.drawColor(Color.argb(255, 68, 68, 68));


                m_PaintBG.setColor(Color.argb(255, 68, 68, 68));
                m_Paint.setColor(Color.argb(255, 160, 200, 200));
                m_PaintX.setColor(Color.argb(255, 255, 80, 80));
                m_PaintRed.setColor(Color.argb(255, 200, 0, 0));
                m_PaintXRed.setColor(Color.argb(255, 255, 0, 0));
                m_PaintBlue.setColor(Color.argb(255, 0, 0, 200));
                m_PaintXBlue.setColor(Color.argb(255, 0, 0, 255));
                m_PaintGreen.setColor(Color.argb(255, 0, 200, 0));
                m_PaintXGreen.setColor(Color.argb(255, 0, 136, 0));
                m_PaintYellow.setColor(Color.argb(255, 200, 200, 0));
                m_PaintXYellow.setColor(Color.argb(255, 188, 188, 0));
                m_PaintPurple.setColor(Color.argb(255, 200, 0, 200));
                m_PaintOrange.setColor(Color.argb(255, 255, 77, 0));
                m_PaintCyan.setColor(Color.argb(255, 0, 200, 200));
                m_PaintBrown.setColor(Color.argb(255, 128, 64, 0));

                m_Paint.setTextSize(300);
                m_PaintX.setTextSize(300);
                m_PaintRed.setTextSize(200);
                m_PaintBlue.setTextSize(200);
                m_PaintGreen.setTextSize(200);
                m_PaintYellow.setTextSize(200);
                m_PaintXRed.setTextSize(200);
                m_PaintXBlue.setTextSize(200);
                m_PaintXGreen.setTextSize(200);
                m_PaintXYellow.setTextSize(200);


                if (hardMode) {m_Canvas.drawText("STAGE:" + stageCount, (m_ScreenWidth / 2) - 600, 400, m_PaintX);

                }else {m_Canvas.drawText("STAGE:" + stageCount, (m_ScreenWidth / 2) - 600, 400, m_Paint);}
                switch(livesLeft) {
                    case 3: m_Canvas.drawText("LIVES LEFT:" + livesLeft, (m_ScreenWidth / 2) - 600, 900, m_PaintXGreen); break;
                    case 2: m_Canvas.drawText("LIVES LEFT:" + livesLeft, (m_ScreenWidth / 2) - 600, 900, m_PaintXYellow); break;
                    case 1: m_Canvas.drawText("LIVES LEFT:" + livesLeft, (m_ScreenWidth / 2) - 600, 900, m_PaintXRed); break;
                    default: m_Canvas.drawText("LIVES LEFT:" + livesLeft, (m_ScreenWidth / 2) - 600, 900, m_PaintXBlue); break;
                }
//                m_Canvas.drawText("COLOR:" + selectedColor, (m_ScreenWidth / 2) - 400, 1000, m_Paint);
//                m_Canvas.drawText("Tst:" + m_Arrows[9], (m_ScreenWidth / 2) - 400, 1500, m_Paint);
//                m_Canvas.drawText("Tst2:" + detectWrongArrow(), (m_ScreenWidth / 2) - 400, 2000, m_Paint);

                //BUTTONS
                m_Canvas.drawRect(0, m_ScreenHeight - (m_ScreenHeight / 8), m_ScreenWidth / 4, m_ScreenHeight, m_PaintRed);
                m_Canvas.drawRect(m_ScreenWidth / 4, m_ScreenHeight - (m_ScreenHeight / 8), m_ScreenWidth / 2, m_ScreenHeight, m_PaintGreen);
                m_Canvas.drawRect(m_ScreenWidth / 2, m_ScreenHeight - (m_ScreenHeight / 8), m_ScreenWidth * 3/ 4, m_ScreenHeight, m_PaintBlue);
                m_Canvas.drawRect(m_ScreenWidth * 3 / 4, m_ScreenHeight - (m_ScreenHeight / 8), m_ScreenWidth, m_ScreenHeight, m_PaintYellow);
                if (hardMode) {
                    m_Canvas.drawRect(0, m_ScreenHeight - (m_ScreenHeight / 4), m_ScreenWidth / 4, m_ScreenHeight - (m_ScreenHeight / 8), m_PaintPurple);
                    m_Canvas.drawRect(m_ScreenWidth / 4, m_ScreenHeight - (m_ScreenHeight / 4), m_ScreenWidth / 2, m_ScreenHeight - (m_ScreenHeight / 8), m_PaintOrange);
                    m_Canvas.drawRect(m_ScreenWidth / 2, m_ScreenHeight - (m_ScreenHeight / 4), m_ScreenWidth * 3 / 4, m_ScreenHeight - (m_ScreenHeight / 8), m_PaintCyan);
                    m_Canvas.drawRect(m_ScreenWidth * 3 / 4, m_ScreenHeight - (m_ScreenHeight / 4), m_ScreenWidth, m_ScreenHeight - (m_ScreenHeight / 8), m_PaintBrown);
                }

                for (int i = 0; i < m_Arrows.length - 1; i++) {
                    switch(m_Arrows[i]) {
                        case 0: m_Canvas.drawRect(i * m_BlockSize, 9 * m_BlockSize, (i * m_BlockSize) + m_BlockSize, (9 * m_BlockSize) + m_BlockSize, m_PaintRed); break;
                        case 1: m_Canvas.drawRect(i * m_BlockSize, 9 * m_BlockSize, (i * m_BlockSize) + m_BlockSize, (9 * m_BlockSize) + m_BlockSize, m_PaintBlue); break;
                        case 2: m_Canvas.drawRect(i * m_BlockSize, 9 * m_BlockSize, (i * m_BlockSize) + m_BlockSize, (9 * m_BlockSize) + m_BlockSize, m_PaintGreen); break;
                        case 3: m_Canvas.drawRect(i * m_BlockSize, 9 * m_BlockSize, (i * m_BlockSize) + m_BlockSize, (9 * m_BlockSize) + m_BlockSize, m_PaintYellow); break;
                        case 4: m_Canvas.drawRect(i * m_BlockSize, 9 * m_BlockSize, (i * m_BlockSize) + m_BlockSize, (9 * m_BlockSize) + m_BlockSize, m_PaintPurple); break;
                        case 5: m_Canvas.drawRect(i * m_BlockSize, 9 * m_BlockSize, (i * m_BlockSize) + m_BlockSize, (9 * m_BlockSize) + m_BlockSize, m_PaintOrange); break;
                        case 6: m_Canvas.drawRect(i * m_BlockSize, 9 * m_BlockSize, (i * m_BlockSize) + m_BlockSize, (9 * m_BlockSize) + m_BlockSize, m_PaintCyan); break;
                        case 7: m_Canvas.drawRect(i * m_BlockSize, 9 * m_BlockSize, (i * m_BlockSize) + m_BlockSize, (9 * m_BlockSize) + m_BlockSize, m_PaintBrown); break;
                        case 99: m_Canvas.drawRect(i * m_BlockSize, 9 * m_BlockSize, (i * m_BlockSize) + m_BlockSize, (9 * m_BlockSize) + m_BlockSize, m_PaintBG); break;
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

        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    if (motionEvent.getY() >= m_ScreenHeight - (m_ScreenHeight / 8)) {
                        if (motionEvent.getX() < m_ScreenWidth / 4) {
                            selectedColor = 0;
                        } else if (motionEvent.getX() >= m_ScreenWidth / 4 && motionEvent.getX() < m_ScreenWidth / 2) {
                            selectedColor = 2;
                        }else if (motionEvent.getX() >= m_ScreenWidth / 2 && motionEvent.getX() < m_ScreenWidth * 3 / 4) {
                            selectedColor = 1;
                        }else if (motionEvent.getX() >= m_ScreenWidth * 3 / 4){
                            selectedColor = 3;
                        }
                    } else if (motionEvent.getY() < m_ScreenHeight - (m_ScreenHeight / 8) && motionEvent.getY() >= m_ScreenHeight - (m_ScreenHeight / 4)) {
                        if (motionEvent.getX() < m_ScreenWidth / 4) {
                            selectedColor = 4;
                        } else if (motionEvent.getX() >= m_ScreenWidth / 4 && motionEvent.getX() < m_ScreenWidth / 2) {
                            selectedColor = 5;
                        } else if (motionEvent.getX() >= m_ScreenWidth / 2 && motionEvent.getX() < m_ScreenWidth * 3 / 4) {
                            selectedColor = 6;
                        } else if (motionEvent.getX() >= m_ScreenWidth * 3 / 4) {
                            selectedColor = 7;
                        }
                    }
            }

            return true;
        }
    }
}
