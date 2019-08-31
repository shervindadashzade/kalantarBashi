package com.example.kalantarbashi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kalantarbashi.Core.Account;

public class LandPageActivity extends AppCompatActivity {
    public static Account myAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_page);

        final SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        TextView username = (TextView)findViewById(R.id.username);
        ImageView imageView = (ImageView)findViewById(R.id.userpic);
        ImageView settings = (ImageView)findViewById(R.id.settings);
        Button goToOurTown = (Button)findViewById(R.id.goToOurTown);
        Button goToRivalTown = (Button)findViewById(R.id.gotToRivalTown);
        Button goToOurTuturial = (Button)findViewById(R.id.goToTuturial);

        myAccount = new Account(sharedPreferences.getString("username",null),sharedPreferences.getString("userPic",null),null);
        username.setText(sharedPreferences.getString("username",null));
        if(sharedPreferences.getString("userPic",null).equals("1")){
            imageView.setImageResource(R.drawable.face1);
        }else{
            imageView.setImageResource(R.drawable.face2);
        }

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LandPageActivity.this,PersonalizeActivity.class));
            }
        });

        goToOurTown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LandPageActivity.this,FindingActivity.class));
            }
        });

        goToRivalTown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LandPageActivity.this,ConnectActivity.class));
            }
        });

        goToOurTuturial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LandPageActivity.this,InformationActivity.class));
            }
        });
    }
}
