package com.example.theodorethayib.hackisu18f;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
//
//class ArrowView extends SurfaceView implements Runnable {
//    private Thread m_Thread = null;
//
//    private volatile boolean m_Playing;
//
//    private Canvas m_Canvas;
//    private SurfaceHolder m_Holder;
//    private Paint m_Paint;
//    private Context m_context;
//
//    public enum Direction {UP, RIGHT, DOWN, LEFT};
//    private Direction m_Direction = Direction.RIGHT;
//
//    private long m_NextFrameTime;
//    private final long FPS = 20;
//
//    private int m_ScreenWidth;
//    private int m_ScreenHeight;
//
//    private int m_Score;
//
//    private int m_BlockSize;
//
//    private int[] m_Arrows;
//
//    private int m_Stage ;
//
//    private int m_Length;
//
//    private int currentArrowDir;
//
//    private final int NUM_BLOCKS_WIDE = 10;
//    private int m_NumBlocksHigh = 1;
//
//    public ArrowView(Context context, Point size) {
//        super(context);
//
//        m_ScreenWidth = size.x;
//        m_ScreenHeight = size.y;
//
//        m_Holder = getHolder();
//        m_Paint = new Paint();
//
//        m_Arrows = new int[10];
//
//        m_BlockSize = m_ScreenWidth / NUM_BLOCKS_WIDE;
//
//        startGame();
//    }
//
//    @Override
//    public void run() {
//        while (m_Playing) {
//            if (checkForUpdate()) {
//                updateGame();
//                drawGame();
//            }
//        }
//    }
//
//    public void pause() {
//        m_Playing = false;
//        try {
//            m_Thread.join();
//        }
//        catch (InterruptedException e) {
//        }
//    }
//
//    public void resume() {
//        m_Playing = true;
//        m_Thread = new Thread(this);
//        m_Thread.start();
//    }
//
//    public void startGame() {
//        m_Stage = 1;
//        m_NextFrameTime = System.currentTimeMillis();
//        currentArrowDir = arrowGameActivity.getArrowDirInt;
//    }
//
//    public void moveArrows() {
//        for (int i = 0; i < m_Arrows.length - 1; i++) {
//            m_Arrows[i] = m_Arrows[i - 1];
//        }
//    }
//
//    public boolean detectWrongArrow () {
//
//
//
//        boolean wrongArrow = false;
//    }
//}