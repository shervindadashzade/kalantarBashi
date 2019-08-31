package com.example.kalantarbashi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kalantarbashi.Core.Account;
import com.example.kalantarbashi.Core.ServerThread;

import java.io.DataOutputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.text.Format;

public class FindingActivity extends AppCompatActivity {
    LinearLayout layout;
    ImageView userPic;
    TextView username;
    TextView ipAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finding);
        ipAdd = (TextView)findViewById(R.id.ipAdd);
        layout = (LinearLayout)findViewById(R.id.foundedUser);
        userPic = (ImageView)findViewById(R.id.foundedUserPic);
        username = (TextView)findViewById(R.id.foundedUsername);
        ipAdd.setText(wifiIpAddress(getApplicationContext()));

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ListeningActivity.account != null)
                    try {
                        DataOutputStream dos = new DataOutputStream(ListeningActivity.account.getSocket().getOutputStream());
                        dos.writeUTF("connect");
                        dos.flush();
                        dos.close();
                        startActivity(new Intent(FindingActivity.this, ListeningActivity.class));
                    }catch (Exception e){

                    }
            }
        });

        ServerThread serverThread = new ServerThread(FindingActivity.this,layout,userPic,username);
        serverThread.execute();
    }
    protected String wifiIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();

        // Convert little-endian to big-endianif needed
        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            ipAddress = Integer.reverseBytes(ipAddress);
        }

        byte[] ipByteArray = BigInteger.valueOf(ipAddress).toByteArray();

        String ipAddressString;
        try {
            ipAddressString = InetAddress.getByAddress(ipByteArray).getHostAddress();
        } catch (UnknownHostException ex) {
            Log.e("WIFIIP", "Unable to get host address.");
            ipAddressString = null;
        }

        return ipAddressString;
    }
}
