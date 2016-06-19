package com.quanganhct.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by quanganh.nguyen on 6/16/2016.
 */
public class MainActivity extends Activity implements DrawingBoardView.DrawingBoardListener {
    private DrawingBoardView mainScreen;
    private TextView txtCount, txtQuestion;
    private boolean gameOn;
    int num=1;
    private View valide, non_valide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.mainScreen = (DrawingBoardView) this.findViewById(R.id.main_screen);
        this.txtCount = (TextView) this.findViewById(R.id.txt_count);
        this.txtQuestion = (TextView) this.findViewById(R.id.txt_demand);
        this.mainScreen.setListener(this);
        this.valide=this.findViewById(R.id.img_valide);
        this.non_valide=this.findViewById(R.id.img_non_valide);
        this.txtQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num=mainScreen.newNumber();
            }
        });
    }


    @Override
    public void onPointerCountChange(int count) {
        //Handle pointer count change here
        this.txtCount.setText(String.format("count: %d", count));
        if(num==count){
            this.valide.setVisibility(View.VISIBLE);
            this.non_valide.setVisibility(View.INVISIBLE);
        }else{
            this.valide.setVisibility(View.INVISIBLE);
            this.non_valide.setVisibility(View.VISIBLE);
        }
    }


}

