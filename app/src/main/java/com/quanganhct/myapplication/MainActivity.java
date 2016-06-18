package com.quanganhct.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by quanganh.nguyen on 6/16/2016.
 */
public class MainActivity extends Activity implements DrawingBoardView.DrawingBoardListener {
    private DrawingBoardView mainScreen;
    private TextView txtCount, txtQuestion;
    private boolean gameOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.mainScreen = (DrawingBoardView) this.findViewById(R.id.main_screen);
        this.txtCount = (TextView) this.findViewById(R.id.txt_count);
        this.txtQuestion = (TextView) this.findViewById(R.id.txt_demand);
        this.mainScreen.setListener(this);
    }


    @Override
    public void onPointerCountChange(int count) {
        //Handle pointer count change here
        this.txtCount.setText(String.format("count: %d", count));
    }
}
