package com.example.kalantarbashi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kalantarbashi.Core.Account;
import com.example.kalantarbashi.Core.GameResult;

public class ResultActivity extends AppCompatActivity {
    public static Account rival;
    public static Account me;
    int mPoint = 0;
    int rPoint = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);

        ImageView meFace = (ImageView)findViewById(R.id.meFace);
        ImageView rivalFace = (ImageView)findViewById(R.id.rivalImg);
        ImageView meResult = (ImageView)findViewById(R.id.meResImg);
        ImageView rivalResult = (ImageView)findViewById(R.id.rivalResImg);
        TextView meUser = (TextView)findViewById(R.id.meId);
        TextView rivalUser = (TextView)findViewById(R.id.rivalId);
        TextView mePoint = (TextView)findViewById(R.id.mePoint);
        TextView rivalPoint = (TextView)findViewById(R.id.rivalPoint);
        Button rePlayGame = (Button)findViewById(R.id.rePlayGame);

        rePlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResultActivity.this,ListeningActivity.class));
            }
        });

        if(rival.getShootInfo().getTime()<me.getShootInfo().getTime()){
            if(-90<=rival.getShootInfo().getPitch() && rival.getShootInfo().getPitch()<=90){
                rival.result = GameResult.WINNER;
                me.result = GameResult.LOSSER;
                rPoint++;
            }
        }else if(rival.getShootInfo().getTime()==me.getShootInfo().getTime()){
            rival.result = GameResult.EQUAL;
            me.result = GameResult.EQUAL;
        }else{
            if(-90<=me.getShootInfo().getPitch() && me.getShootInfo().getPitch()<=90){
                me.result = GameResult.WINNER;
                rival.result = GameResult.LOSSER;
                mPoint++;
            }
        }

        meUser.setText(me.getName());
        mePoint.setText(Integer.toString(mPoint));
        rivalUser.setText(rival.getName());
        rivalPoint.setText(Integer.toString(rPoint));

        if(rival.getUserPic().equals("1")){
            rivalFace.setImageResource(R.drawable.face1);
        }else{
            rivalFace.setImageResource(R.drawable.face2);
        }

        if(me.getUserPic().equals("1")){
            meFace.setImageResource(R.drawable.face1);
        }else{
            meFace.setImageResource(R.drawable.face2);
        }

        if(me.result == GameResult.LOSSER){
            meResult.setImageResource(R.drawable.ic_dead);
        }else{
            meResult.setImageResource(R.drawable.ic_gun);
        }
        if(rival.result == GameResult.LOSSER){
            rivalResult.setImageResource(R.drawable.ic_dead);
        }else{
            rivalResult.setImageResource(R.drawable.ic_gun);
        }
    }
}
