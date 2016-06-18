package com.quanganhct.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
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
    int currentCount = 1;
    private HashMap<Integer, MyPoint> maps = new HashMap<>();
    private Random random;
    private Paint paint;

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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (MyPoint p : maps.values()) {
            this.paint.setARGB(255, p.r, p.g, p.b);
            canvas.drawCircle(p.x, p.y, 100, paint);
        }
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
        invalidate();
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
