package com.example.orderfood.activity.boss.listen;

import android.view.View;
import android.widget.ImageView;

import com.example.orderfood.R;
//待写
public class StartListen implements View.OnClickListener{
    @Override
    public void onClick(View v){
        int idT=v.getId();
        ImageView img= (ImageView) v;
        img.setImageResource(R.drawable.wxx);
    }

    private int score;
    public StartListen(int score){
        this.score=score;
    }
}
