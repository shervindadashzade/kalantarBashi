package com.example.kalantarbashi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kalantarbashi.Core.ConnectThread;

import java.io.DataOutputStream;
import java.net.Socket;

public class ConnectActivity extends AppCompatActivity {
    String ip;
    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        final SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        final EditText editText = (EditText)findViewById(R.id.ipAddress);
        Button connect = (Button)findViewById(R.id.connectBtn);
        data = String.format("%s@%s",sharedPreferences.getString("username",null),sharedPreferences.getString("userPic",null));
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    ip = editText.getText().toString();
                    ConnectThread connectThread = new ConnectThread(ip,data,ConnectActivity.this);
                    connectThread.execute();
            }
        });
    }
}
