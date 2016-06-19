package com.quanganhct.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by quanganh.nguyen on 6/18/2016.
 */
public class DrawingBoardView extends View {
    private Context context;
    private DrawingBoardListener listener;
    private HashMap<Integer, MyPoint> maps = new HashMap<>();
    private Random random;
    private Paint paint;
    private final int RADIUS = 100;
    private final int MAX_RADIUS = 150;
    private int sign = 1;
    private int currentRadius = 100;
    private Handler handler = new Handler();
    private int newNum = 0;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    Bitmap[] finger = new Bitmap[9];

    public static interface DrawingBoardListener {
        void onPointerCountChange(int count);
    }

    public DrawingBoardView(Context context) {
        super(context);
        this.init(context);
    }

    public DrawingBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public int newNumber() {
        newNum = random.nextInt(10);
        return newNum;
    }

    public DrawingBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context);
    }

    public void setListener(DrawingBoardListener listener) {
        this.listener = listener;
    }

    private void init(Context context) {
        this.context = context;
        this.random = new Random();
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        finger[0] = ((BitmapDrawable) getResources().getDrawable(R.drawable.finger1)).getBitmap();
        finger[1] = ((BitmapDrawable) getResources().getDrawable(R.drawable.finger2)).getBitmap();
        finger[2] = ((BitmapDrawable) getResources().getDrawable(R.drawable.finger3)).getBitmap();
        finger[3] = ((BitmapDrawable) getResources().getDrawable(R.drawable.finger4)).getBitmap();
        finger[4] = ((BitmapDrawable) getResources().getDrawable(R.drawable.finger5)).getBitmap();
        finger[5] = ((BitmapDrawable) getResources().getDrawable(R.drawable.finger6)).getBitmap();
        finger[6] = ((BitmapDrawable) getResources().getDrawable(R.drawable.finger7)).getBitmap();
        finger[7] = ((BitmapDrawable) getResources().getDrawable(R.drawable.finger8)).getBitmap();
        finger[8] = ((BitmapDrawable) getResources().getDrawable(R.drawable.finger9)).getBitmap();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect Cube = new Rect();
        Cube.set((int) 150, (int) 150, (int) 750, (int) 750);

        canvas.drawBitmap(finger[newNum], null, Cube, paint);

        for (MyPoint p : maps.values()) {
            this.paint.setARGB(255, p.r, p.g, p.b);
            this.paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(p.x, p.y, RADIUS, paint);
            this.paint.setARGB(80, p.r, p.g, p.b);
            canvas.drawCircle(p.x, p.y, currentRadius, paint);
        }

        if (currentRadius < RADIUS) {
            currentRadius = RADIUS;
            sign = 1;
        } else if (currentRadius > MAX_RADIUS) {
            sign = -1;
        }
        currentRadius += sign * 4;
        handler.postDelayed(runnable, 40);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        int index = MotionEventCompat.getActionIndex(event);
        int id = event.getPointerId(index);
        int count = event.getPointerCount();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                //register new pointer
                MyPoint p = new MyPoint();
                p.x = event.getX(index);
                p.y = event.getY(index);
                maps.put(id, p);
                if (listener != null) {
                    listener.onPointerCountChange(count);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //update pointer position
                for (int i = 0; i < event.getPointerCount(); i++) {
                    p = maps.get(event.getPointerId(i));
                    if (p != null) {
                        p.x = event.getX(i);
                        p.y = event.getY(i);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                maps.remove(id);
                if (listener != null) {
                    listener.onPointerCountChange(count - 1);
                }
                break;
        }
//        invalidate();
        Log.w("On Touch", String.format("action: %s, index: %d, count: %d, id: %d", actionToString(action), index, event.getPointerCount(), id));
        return true;
    }

    public static String actionToString(int action) {
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                return "Down";
            case MotionEvent.ACTION_MOVE:
                return "Move";
            case MotionEvent.ACTION_POINTER_DOWN:
                return "Pointer Down";
            case MotionEvent.ACTION_UP:
                return "Up";
            case MotionEvent.ACTION_POINTER_UP:
                return "Pointer Up";
            case MotionEvent.ACTION_OUTSIDE:
                return "Outside";
            case MotionEvent.ACTION_CANCEL:
                return "Cancel";
        }
        return "";
    }

    private class MyPoint {
        float x, y;
        int r, g, b;

        private MyPoint() {
            r = random.nextInt(256);
            g = random.nextInt(256);
            b = random.nextInt(256);
        }
    }
}
