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
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };
    Bitmap doigt1;
    Bitmap doigt2;
    Bitmap doigt3;
    Bitmap doigt4;
    Bitmap doigt5;
    Bitmap doigt6;
    Bitmap doigt7;
    Bitmap doigt8;
    Bitmap doigt9;
    int alea;




    public static interface DrawingBoardListener {
        void onPointerCountChange(int count);
    }

    public DrawingBoardView(Context context) {
        super(context);
        this.init(context);
    }

    public DrawingBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        doigt1=((BitmapDrawable) getResources().getDrawable(R.drawable.finger1)).getBitmap();
        doigt2=((BitmapDrawable) getResources().getDrawable(R.drawable.finger2)).getBitmap();
        doigt3=((BitmapDrawable) getResources().getDrawable(R.drawable.finger3)).getBitmap();
        doigt4=((BitmapDrawable) getResources().getDrawable(R.drawable.finger4)).getBitmap();
        doigt5=((BitmapDrawable) getResources().getDrawable(R.drawable.finger5)).getBitmap();
        doigt6=((BitmapDrawable) getResources().getDrawable(R.drawable.finger6)).getBitmap();
        doigt7=((BitmapDrawable) getResources().getDrawable(R.drawable.finger7)).getBitmap();
        doigt8=((BitmapDrawable) getResources().getDrawable(R.drawable.finger8)).getBitmap();
        doigt9=((BitmapDrawable) getResources().getDrawable(R.drawable.finger9)).getBitmap();
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
        Paint paint = new Paint();
        Rect Cube= new Rect();
        Rect Cube2= new Rect();
        Cube.set(200,150,800,750);
        Cube2.set(200,800,800,1400);
        Random rand = new Random();
        alea = rand.nextInt(10);

        if (alea==1) {
            canvas.drawBitmap(doigt1, null, Cube, paint);
        }
        if (alea==2) {
            canvas.drawBitmap(doigt2, null, Cube, paint);
        }
        if (alea==3) {
            canvas.drawBitmap(doigt3, null, Cube, paint);
        }
        if (alea==4) {
            canvas.drawBitmap(doigt4, null, Cube, paint);
        }
        if (alea==5) {
            canvas.drawBitmap(doigt5, null, Cube, paint);
        }
        if (alea==6) {
            canvas.drawBitmap(doigt6, null, Cube, paint);
        }
        if (alea==7) {
            canvas.drawBitmap(doigt7, null, Cube, paint);
        }
        if (alea==8) {
            canvas.drawBitmap(doigt8, null, Cube, paint);
        }
        if (alea==9) {
            canvas.drawBitmap(doigt9, null, Cube, paint);
        }
        if (alea==9) {
            canvas.drawBitmap(doigt5, null, Cube, paint);
            canvas.drawBitmap(doigt5, null, Cube2, paint);
        }

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
