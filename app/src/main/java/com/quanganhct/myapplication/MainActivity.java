package com.quanganhct.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by quanganh.nguyen on 6/16/2016.
 */
public class MainActivity extends Activity {
    private View mainScreen;
    private TextView txtCount, txtQuestion;
    private boolean gameOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.mainScreen = this.findViewById(R.id.main_screen);
        this.txtCount = (TextView) this.findViewById(R.id.txt_count);
        this.txtQuestion = (TextView) this.findViewById(R.id.txt_demand);

//        this.mainScreen.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
////                handleEvent(v, event);
//                int action = MotionEventCompat.getActionMasked(event);
//                int index = MotionEventCompat.getActionIndex(event);
//                Log.w("On Touch", String.format("action: %s, index: %d, count: %d", DrawingBoardView.actionToString(action), index, event.getPointerCount()));
//                return true;
//            }
//        });
    }

    private void handleEvent(View view, MotionEvent event) {
        int count = event.getPointerCount();
        if (count > 1) {
            this.txtCount.setText(String.format("%d fingers", count));
        } else {
            this.txtCount.setText("");
        }
    }
}
